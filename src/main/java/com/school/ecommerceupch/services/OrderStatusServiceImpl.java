package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerceupch.entities.OrderStatus;
import com.school.ecommerceupch.repositories.IOrderStatusRepository;
import com.school.ecommerceupch.services.interfaces.IOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusServiceImpl implements IOrderStatusService {
    @Autowired
    private IOrderStatusRepository repository;

    @Override
    public BaseResponse list() {
        List<OrderStatus> orderStatus = repository.findAll();

        return BaseResponse.builder()
                .data(orderStatus)
                .message("Ordes status found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public OrderStatus findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Order Status not found"));
    }

    @Override
    public OrderStatus findOneAndEnsureExistByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ObjectNotFoundException("Order Status not found"));
    }

}
