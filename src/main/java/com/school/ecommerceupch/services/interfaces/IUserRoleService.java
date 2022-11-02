package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateUserRoleRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateUserRoleRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.exceptions.UserRoleAlreadyExistsException;
import com.school.ecommerceupch.controllers.exceptions.UserRoleNotFoundException;
import com.school.ecommerceupch.entities.UserRole;

public interface IUserRoleService {
    BaseResponse create(CreateUserRoleRequest request) throws UserRoleAlreadyExistsException;
    BaseResponse get(Long id) throws UserRoleNotFoundException;
    BaseResponse update(Long id, UpdateUserRoleRequest request) throws UserRoleNotFoundException;
    BaseResponse delete(Long id) throws UserRoleNotFoundException;

    UserRole findByName(String name);

}
