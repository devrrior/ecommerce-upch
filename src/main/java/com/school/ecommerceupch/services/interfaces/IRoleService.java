package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateRoleRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateRoleRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Role;

public interface IRoleService {

    BaseResponse list();

    Role findOneAndEnsureExistById(Long id);

    Role findOneAndEnsureExistByName(String name);

}
