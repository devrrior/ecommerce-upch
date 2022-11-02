package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateUserRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateUserRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;

public interface IUserService {
    BaseResponse create(CreateUserRequest request);

    BaseResponse get(Long id);

    BaseResponse update(UpdateUserRequest request, Long id);

    BaseResponse delete(Long id);

}
