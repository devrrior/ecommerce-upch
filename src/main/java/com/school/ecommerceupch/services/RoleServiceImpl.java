package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateRoleRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateRoleRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerceupch.entities.Role;
import com.school.ecommerceupch.repositories.IRoleRepository;
import com.school.ecommerceupch.services.interfaces.IRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository repository;

    public RoleServiceImpl(IRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public BaseResponse create(CreateRoleRequest request) {
        Role role = repository.save(from(request));
        return BaseResponse.builder()
                .data(role)
                .message("User Role created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse get(Long id) {
        Role role = findOneAndEnsureExistById(id);

        return BaseResponse.builder()
                .data(role)
                .message("User Role exists")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateRoleRequest request) {
        Role role = findOneAndEnsureExistById(id);
        role = update(role, request);

        return BaseResponse.builder()
                .data(role)
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
    public Role findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User Role not found"));
    }

    @Override
    public Role findOneAndEnsureExistByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ObjectNotFoundException("User Role not found"));
    }

    private Role from(CreateRoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        return role;
    }

    private Role update(Role role, UpdateRoleRequest request) {
        role.setName(request.getName());
        return repository.save(role);
    }

}
