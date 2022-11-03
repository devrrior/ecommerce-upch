package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateUserRoleRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateUserRoleRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Role;

public interface IRoleService {
    BaseResponse create(CreateUserRoleRequest request);

    BaseResponse get(Long id);

    BaseResponse update(Long id, UpdateUserRoleRequest request);

    BaseResponse delete(Long id);

    Role findOneAndEnsureExistById(Long id);

    Role findOneAndEnsureExistByName(String name);

}
