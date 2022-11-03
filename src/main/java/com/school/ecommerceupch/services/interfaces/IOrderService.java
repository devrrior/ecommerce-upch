package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Order;

public interface IOrderService {

    BaseResponse create(CreateOrderRequest request);
    BaseResponse get(Long id);
    BaseResponse update(Long Id, UpdateOrderRequest request);
    BaseResponse delete(Long Id);


    Order findOneAndEnsureExistById(Long id);
}
