package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateUserRoleRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateUserRoleRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.UserRole;
import com.school.ecommerceupch.repositories.IUserRoleRepository;
import com.school.ecommerceupch.services.interfaces.IUserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
    private final IUserRoleRepository repository;

    public UserRoleServiceImpl(IUserRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public BaseResponse create(CreateUserRoleRequest request) {
        UserRole userRole = repository.save(from(request));
        return BaseResponse.builder()
                .data(userRole)
                .message("User Role created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse get(Long id) {
        UserRole userRole = findOneAndEnsureExistById(id);

        return BaseResponse.builder()
                .data(userRole)
                .message("User Role exists")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateUserRoleRequest request) {
        UserRole userRole = findOneAndEnsureExistById(id);
        userRole = update(userRole, request);

        return BaseResponse.builder()
                .data(userRole)
                .message("User Role updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {
        repository.deleteById(id);

        return BaseResponse.builder()
                .data(null)
                .message("User Role deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public UserRole findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User Role not found"));
    }

    @Override
    public UserRole findOneAndEnsureExistByName(String name) {
        return repository.findByName(name).orElseThrow(()-> new RuntimeException("User Role not found"));
    }

    private UserRole from(CreateUserRoleRequest request) {
        UserRole userRole = new UserRole();
        userRole.setName(request.getName());
        return userRole;
    }

    private UserRole update(UserRole userRole, UpdateUserRoleRequest request) {
        userRole.setName(request.getName());
        return repository.save(userRole);
    }

}
