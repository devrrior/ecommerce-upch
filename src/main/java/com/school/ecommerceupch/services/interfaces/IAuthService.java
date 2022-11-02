package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.AuthenticationRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;

public interface IAuthService {
    BaseResponse authenticate(AuthenticationRequest request);
}
