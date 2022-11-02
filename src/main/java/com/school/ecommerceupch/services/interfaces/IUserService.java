package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateUserRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateUserRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.exceptions.UserAlreadyExistsException;
import com.school.ecommerceupch.controllers.exceptions.UserNotFoundException;

public interface IUserService {
    BaseResponse create(CreateUserRequest request) throws UserAlreadyExistsException;
    BaseResponse get(Long id) throws UserNotFoundException;
    BaseResponse update(UpdateUserRequest request, Long id) throws UserNotFoundException, UserAlreadyExistsException;
    BaseResponse delete(Long id) throws UserNotFoundException;

}
