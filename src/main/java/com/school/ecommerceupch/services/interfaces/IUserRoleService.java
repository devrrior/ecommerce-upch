package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Role;
import com.school.ecommerceupch.entities.pivots.UserRole;

import java.util.List;

public interface IUserRoleService {

    BaseResponse listAllUsersByRoleId(Long id);

    BaseResponse listAllRolesByUserId(Long id);

    List<Role> getAllRolesByUserId(Long id);

    UserRole create(Long userId, Long roleId);
}
