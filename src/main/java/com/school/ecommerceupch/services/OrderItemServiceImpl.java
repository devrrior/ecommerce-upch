package com.school.ecommerceupch.services;


import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderItemRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderItemRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.exceptions.AccessDeniedException;
import com.school.ecommerceupch.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerceupch.entities.Order;
import com.school.ecommerceupch.entities.OrderItem;
import com.school.ecommerceupch.entities.Product;
import com.school.ecommerceupch.repositories.IOrderItemRepository;
import com.school.ecommerceupch.security.UserDetailsImpl;
import com.school.ecommerceupch.services.interfaces.IOrderItemService;
import com.school.ecommerceupch.services.interfaces.IOrderService;
import com.school.ecommerceupch.services.interfaces.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class OrderItemServiceImpl implements IOrderItemService {

    private final IOrderItemRepository repository;

    private final IProductService productService;

    private final IOrderService orderService;

    public OrderItemServiceImpl(IOrderItemRepository repository, IProductService productService, IOrderService orderService) {
        this.repository = repository;
        this.productService = productService;
        this.orderService = orderService;
    }

    private static UserDetailsImpl getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Override
    public BaseResponse create(CreateOrderItemRequest request) {

        Product product = productService.findOneAndEnsureExists(request.getProductId());
        Order order = orderService.findOneAndEnsureExistById(request.getOrderId());
        OrderItem orderItem = repository.save(from(request, product, order));

        return BaseResponse.builder()
                .data(orderItem)
                .message("OrderItem saved correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();

    }

    @Override
    public BaseResponse get(Long id) {

        OrderItem orderItem = findOneAndEnsureExistById(id);

        return BaseResponse.builder()
                .data(orderItem)
                .message("OrderItem found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateOrderItemRequest request) {

        UserDetailsImpl userAuthenticated = getUserAuthenticated();

        OrderItem orderItem = findOneAndEnsureExistById(id);

        if (!orderItem.getOrder().getUser().getId().equals(userAuthenticated.getUser().getId()))
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

        UserDetailsImpl userAuthenticated = getUserAuthenticated();

        OrderItem orderItem = findOneAndEnsureExistById(id);

        if (!orderItem.getOrder().getUser().getId().equals(userAuthenticated.getUser().getId()))
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
        orderItem.setProduct(request.getProduct());
        orderItem.setProduct(request.getProduct());
        repository.save(orderItem);
        return orderItem;
    }
}
