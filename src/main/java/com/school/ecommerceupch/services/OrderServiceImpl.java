package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Order;
import com.school.ecommerceupch.repositories.IOrderRepository;
import com.school.ecommerceupch.services.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderRepository repository;

    @Override
    public BaseResponse create(CreateOrderRequest request) {
        Order order = repository.save(from(request));

        return BaseResponse.builder()
                .data(order)
                .message("Order created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
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
    public BaseResponse update(Long Id, UpdateOrderRequest request){
        Order order = findOneAndEnsureExistById(Id);
        order = update(order, request);
        return BaseResponse.builder()
                .data(order)
                .message("Order updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long Id){
        repository.deleteById(Id);

        return BaseResponse.builder()
                .data(null)
                .message("Order deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public Order findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }
    private Order from(CreateOrderRequest request) {
        Order order = new Order();
        order.setStatus(request.getStatus());
        return order;
    }

    private Order update(Order order, UpdateOrderRequest request) {
        order.setStatus(request.getStatus());
        return repository.save(order);
    }
}
