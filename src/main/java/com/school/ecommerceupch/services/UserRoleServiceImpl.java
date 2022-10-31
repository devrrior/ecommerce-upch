package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateUserRoleRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateUserRoleRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.dtos.responses.GetUserRoleResponse;
import com.school.ecommerceupch.entities.UserRole;
import com.school.ecommerceupch.repositories.IUserRoleRepository;
import com.school.ecommerceupch.services.interfaces.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
    @Autowired
    private IUserRoleRepository repository;
    @Override
    public BaseResponse create(CreateUserRoleRequest request) {
        if(repository.existsUserRoleByName(request.getName())) {
            return BaseResponse.builder()
                    .data(null)
                    .message("User Role already exists")
                    .success(Boolean.FALSE)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
        return BaseResponse.builder()
                .data(toGetUserRoleResponse(repository.save(toUserRole(request))))
                .message("User Role created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse get(Long id) {
        if (!repository.existsById(id)) {
            return userRoleDoesntExists();
        }
        return BaseResponse.builder()
                .data(toGetUserRoleResponse(repository.getUserRoleById(id)))
                .message("User Role exists")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateUserRoleRequest request) {
        if (!repository.existsById(id)) {
            return userRoleDoesntExists();
        }
        return BaseResponse.builder()
                .data(toGetUserRoleResponse(repository.save(toUserRoleUpdate(request, id))))
                .message("User Role updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {
        if(!repository.existsById(id)) {
            return userRoleDoesntExists();
        }
        repository.deleteById(id);
        return BaseResponse.builder()
                .data(null)
                .message("User Role deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private BaseResponse userRoleDoesntExists() {
        return BaseResponse.builder()
                .data(null)
                .message("User Role doesn't exists")
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }

    private GetUserRoleResponse toGetUserRoleResponse(UserRole userRole) {
        GetUserRoleResponse response = new GetUserRoleResponse();
        response.setName(userRole.getName());
        response.setId(userRole.getId());
        return response;
    }

    private UserRole toUserRole(CreateUserRoleRequest request) {
        UserRole userRole = new UserRole();
        userRole.setName(request.getName());
        return userRole;
    }

    private UserRole toUserRoleUpdate(UpdateUserRoleRequest request, Long id) {
        UserRole userRole = repository.getUserRoleById(id);
        userRole.setName(request.getName());
        return userRole;
    }

    @Override
    public UserRole findByName(String name) {
        return repository.findByName(name);
    }
}
