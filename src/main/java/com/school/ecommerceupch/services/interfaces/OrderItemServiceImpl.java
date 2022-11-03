package com.school.ecommerceupch.services.interfaces;


import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderItemRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderItemRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl  implements IOrderItemService {


    @Override
    public BaseResponse create(CreateOrderItemRequest request) {
        return null;
    }

    @Override
    public BaseResponse get(Long id) {
        return null;
    }

    @Override
    public BaseResponse update(Long id, UpdateOrderItemRequest request) {
        return null;
    }

    @Override
    public BaseResponse delete(Long id) {
        return null;
    }
}
