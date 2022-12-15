package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CheckoutRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;

public interface ICheckoutService {

    BaseResponse checkout(CheckoutRequest request);
}
