package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderRequest;
import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderStatusRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderStatusRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.OrderStatus;

public interface IOrderStatusService {
    BaseResponse create(CreateOrderStatusRequest request);
    BaseResponse get(Long id);
    BaseResponse update(Long Id, UpdateOrderStatusRequest request);
    BaseResponse delete(Long Id);

    OrderStatus findOneAndEnsureExistById(Long id);
}
