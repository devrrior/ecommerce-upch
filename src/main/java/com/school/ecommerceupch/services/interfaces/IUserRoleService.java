package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;

public interface IUserRoleService {

    BaseResponse listAllUsersByRoleId(Long id);

    BaseResponse listAllRolesByUserId(Long id);
}
