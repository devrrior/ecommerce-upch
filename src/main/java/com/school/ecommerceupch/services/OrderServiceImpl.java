package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.exceptions.AccessDeniedException;
import com.school.ecommerceupch.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerceupch.entities.Order;
import com.school.ecommerceupch.entities.OrderStatus;
import com.school.ecommerceupch.repositories.IOrderRepository;
import com.school.ecommerceupch.security.UserDetailsImpl;
import com.school.ecommerceupch.services.interfaces.IOrderService;
import com.school.ecommerceupch.services.interfaces.IOrderStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository repository;

    private final IOrderStatusService orderStatusService;

    public OrderServiceImpl(IOrderRepository repository, IOrderStatusService orderStatusService) {
        this.repository = repository;
        this.orderStatusService = orderStatusService;
    }

    private static UserDetailsImpl getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Override
    public BaseResponse create() {

        UserDetailsImpl userAuthenticated = getUserAuthenticated();

        OrderStatus defaultOrderStatus = orderStatusService.findOneAndEnsureExistByName("PENDING");

        Order order = new Order();
        order.setOrderStatus(defaultOrderStatus);
        order.setUser(userAuthenticated.getUser());

        order = repository.save(order);

        return BaseResponse.builder()
                .data(order)
                .message("Order created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public BaseResponse get(Long id) {
        Order order = findOneAndEnsureExistById(id);

        return BaseResponse.builder()
                .data(order)
                .message("Order already exists")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateOrderRequest request) {

        UserDetailsImpl userDetails = getUserAuthenticated();

        Order order = findOneAndEnsureExistById(id);

        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                || !order.getUser().getId().equals(userDetails.getUser().getId()))
            throw new AccessDeniedException();

        OrderStatus orderStatus = orderStatusService.findOneAndEnsureExistById(request.getOrderStatusId());

        order = update(order, orderStatus);

        return BaseResponse.builder()
                .data(order)
                .message("Order updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {

        UserDetailsImpl userDetails = getUserAuthenticated();

        Order order = findOneAndEnsureExistById(id);

        if (!order.getUser().getId().equals(userDetails.getUser().getId()))
            throw new AccessDeniedException();

        repository.deleteById(id);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .message("Order deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public Order findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Order not found"));
    }

    private Order update(Order order, OrderStatus orderStatus) {
        order.setOrderStatus(orderStatus);
        return repository.save(order);
    }

}
