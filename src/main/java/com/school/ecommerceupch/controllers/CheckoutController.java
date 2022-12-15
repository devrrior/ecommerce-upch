package com.school.ecommerceupch.controllers;

import com.school.ecommerceupch.controllers.dtos.requests.CheckoutRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.services.interfaces.ICheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/checkout")
public class CheckoutController {

    @Autowired
    private ICheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<BaseResponse> checkout(@RequestBody CheckoutRequest request) {
        BaseResponse baseResponse = checkoutService.checkout(request);

        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

}
