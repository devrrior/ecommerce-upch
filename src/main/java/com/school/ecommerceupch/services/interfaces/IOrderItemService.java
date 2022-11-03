package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderItemRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderItemRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;

public interface IOrderItemService {

    BaseResponse create (CreateOrderItemRequest request);
    BaseResponse get (Long id);
    BaseResponse update (Long id, UpdateOrderItemRequest request);
    BaseResponse delete (Long id);
}
