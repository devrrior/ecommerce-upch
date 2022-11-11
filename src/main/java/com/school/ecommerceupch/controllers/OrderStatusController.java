package com.school.ecommerceupch.controllers;


import com.school.ecommerceupch.controllers.dtos.requests.CreateOrderStatusRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateOrderStatusRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.services.interfaces.IOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/order-status")
@RestController
public class OrderStatusController {
    @Autowired
    private IOrderStatusService service;

    @GetMapping
    ResponseEntity<BaseResponse> list() {
        BaseResponse baseResponse = service.list();

        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }
}
