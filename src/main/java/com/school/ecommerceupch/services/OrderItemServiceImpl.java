package com.school.ecommerceupch.services;


import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderItemRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderItemRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.exceptions.AccessDeniedException;
import com.school.ecommerceupch.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerceupch.entities.Order;
import com.school.ecommerceupch.entities.OrderItem;
import com.school.ecommerceupch.entities.Product;
import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.repositories.IOrderItemRepository;
import com.school.ecommerceupch.security.UserDetailsImpl;
import com.school.ecommerceupch.services.interfaces.IOrderItemService;
import com.school.ecommerceupch.services.interfaces.IOrderService;
import com.school.ecommerceupch.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class OrderItemServiceImpl implements IOrderItemService {

    @Autowired
    private IOrderItemRepository repository;

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderService orderService;

    private static UserDetailsImpl getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Override
    public BaseResponse create(CreateOrderItemRequest request) {

        OrderItem orderItem;

        User userAuthenticated = getUserAuthenticated().getUser();

        Order order = orderService.findOneByUserIdOrCreate(userAuthenticated.getId());
        OrderItem orderItemAlreadyExists = getOrderItemIfAlreadyExists(request, order);

        if (orderItemAlreadyExists == null) {
            Product product = productService.findOneAndEnsureExists(request.getProductId());

            orderItem = repository.save(from(request, product, order));
        } else {
            orderItemAlreadyExists.setQuantity(orderItemAlreadyExists.getQuantity() + request.getQuantity());
            orderItem = repository.save(orderItemAlreadyExists);
        }


        return BaseResponse.builder()
                .data(orderItem)
                .message("OrderItem saved correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();

    }

    private static OrderItem getOrderItemIfAlreadyExists(CreateOrderItemRequest request, Order order) {
        return order.getOrderItems().stream()
                .filter(orderItem1 ->
                        orderItem1.getProduct().getId().equals(request.getProductId())).findAny().orElse(null);
    }

    @Override
    public BaseResponse get(Long id) {

        UserDetailsImpl userDetails = getUserAuthenticated();

        OrderItem orderItem = findOneAndEnsureExistById(id);

        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                && !userDetails.getUser().getId().equals(id))
            throw new AccessDeniedException();


        return BaseResponse.builder()
                .data(orderItem)
                .message("OrderItem found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateOrderItemRequest request) {

        User userAuthenticated = getUserAuthenticated().getUser();

        OrderItem orderItem = findOneAndEnsureExistById(id);

        if (!orderItem.getOrder().getUser().getId().equals(userAuthenticated.getId()))
            throw new AccessDeniedException();

        orderItem = update(orderItem, request);
        return BaseResponse.builder()
                .data(orderItem)
                .message("OrderItem update")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {

        User userAuthenticated = getUserAuthenticated().getUser();

        OrderItem orderItem = findOneAndEnsureExistById(id);

        if (!orderItem.getOrder().getUser().getId().equals(userAuthenticated.getId()))
            throw new AccessDeniedException();

        repository.deleteById(id);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .message("OrderItem deleted correctly ")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    private OrderItem findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("The OrderItem does not exist"));
    }

    private OrderItem from(CreateOrderItemRequest request, Product product, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(request.getQuantity());
        orderItem.setProduct(product);
        orderItem.setOrder(order);

        return orderItem;
    }

    private OrderItem update(OrderItem orderItem, UpdateOrderItemRequest request) {

        orderItem.setQuantity(request.getQuantity());
        repository.save(orderItem);
        return orderItem;
    }
}
