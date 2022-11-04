package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateRoleRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateRoleRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Role;

public interface IRoleService {
    BaseResponse create(CreateRoleRequest request);

    BaseResponse get(Long id);

    BaseResponse update(Long id, UpdateRoleRequest request);

    BaseResponse delete(Long id);

    Role findOneAndEnsureExistById(Long id);

    Role findOneAndEnsureExistByName(String name);

}
