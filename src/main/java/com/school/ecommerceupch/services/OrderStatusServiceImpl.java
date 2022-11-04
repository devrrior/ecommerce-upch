package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderStatusRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderStatusRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.OrderStatus;
import com.school.ecommerceupch.repositories.IOrderStatusRepository;
import com.school.ecommerceupch.services.interfaces.IOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusServiceImpl implements IOrderStatusService {
    @Autowired
    private IOrderStatusRepository repository;
    @Override
    public BaseResponse create(CreateOrderStatusRequest request) {
        OrderStatus orderStatus = repository.save(from(request));

        return BaseResponse.builder()
                .data(orderStatus)
                .message("Order Status created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
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
        repository.deleteById(id);

        return BaseResponse.builder()
                .data(null)
                .message("Order deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public OrderStatus findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Order Status not found"));
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
