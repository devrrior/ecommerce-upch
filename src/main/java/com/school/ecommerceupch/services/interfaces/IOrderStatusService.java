package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderStatusRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderStatusRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.OrderStatus;

public interface IOrderStatusService {

    BaseResponse list();

    OrderStatus findOneAndEnsureExistById(Long id);

    OrderStatus findOneAndEnsureExistByName(String name);
}
