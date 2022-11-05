package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderStatusRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderStatusRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerceupch.controllers.exceptions.UniqueConstraintViolationException;
import com.school.ecommerceupch.entities.OrderStatus;
import com.school.ecommerceupch.repositories.IOrderStatusRepository;
import com.school.ecommerceupch.services.interfaces.IOrderStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class OrderStatusServiceImpl implements IOrderStatusService {
    private final IOrderStatusRepository repository;

    public OrderStatusServiceImpl(IOrderStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public BaseResponse create(CreateOrderStatusRequest request) {

        if (repository.existsByName(request.getName()))
            throw new UniqueConstraintViolationException("Name is already in use");

        OrderStatus orderStatus = repository.save(from(request));

        return BaseResponse.builder()
                .data(orderStatus)
                .message("Order Status created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public BaseResponse get(Long id) {
        OrderStatus orderStatus = findOneAndEnsureExistById(id);

        return BaseResponse.builder()
                .data(orderStatus)
                .message("Order Status already exists")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateOrderStatusRequest request) {
        OrderStatus orderStatus = findOneAndEnsureExistById(id);
        orderStatus = update(orderStatus, request);

        return BaseResponse.builder()
                .data(orderStatus)
                .message("Order Status updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {

        if (!repository.existsById(id))
            throw new ObjectNotFoundException("Order status not found");

        repository.deleteById(id);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .message("Order deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public OrderStatus findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Order Status not found"));
    }

    @Override
    public OrderStatus findOneAndEnsureExistByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ObjectNotFoundException("Order Status not found"));
    }

    private OrderStatus from(CreateOrderStatusRequest request) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setName(request.getName());
        return orderStatus;
    }

    private OrderStatus update(OrderStatus orderStatus, UpdateOrderStatusRequest request) {
        orderStatus.setName(request.getName());
        return repository.save(orderStatus);
    }

}
