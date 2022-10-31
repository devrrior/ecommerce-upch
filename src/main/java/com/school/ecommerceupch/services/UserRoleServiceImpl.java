package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateUserRoleRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateUserRoleRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.UserRole;
import com.school.ecommerceupch.repositories.IUserRoleRepository;
import com.school.ecommerceupch.services.interfaces.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
    @Autowired
    private IUserRoleRepository repository;
    @Override
    public BaseResponse create(CreateUserRoleRequest request) {
        return null;
    }

    @Override
    public BaseResponse get(Long id) {
        return null;
    }

    @Override
    public BaseResponse update(Long id, UpdateUserRoleRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserRole findByName(String name) {
        return repository.findByName(name);
    }
}
