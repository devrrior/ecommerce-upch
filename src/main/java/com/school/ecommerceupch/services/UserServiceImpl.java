package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateUserRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateUserRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.entities.Role;
import com.school.ecommerceupch.repositories.IUserRepository;
import com.school.ecommerceupch.services.interfaces.IRoleService;
import com.school.ecommerceupch.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository repository;
    private final IRoleService userRoleService;

    public UserServiceImpl(IUserRepository repository, IRoleService userRoleService) {
        this.repository = repository;
        this.userRoleService = userRoleService;
    }

    @Override
    public BaseResponse create(CreateUserRequest request) {
        User user = repository.save(from(request));

        return BaseResponse.builder()
                .data(user)
                .message("User created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public BaseResponse get(Long id) {

        User user = findOneAndEnsureExistById(id);

        return BaseResponse.builder()
                .data(user)
                .message("User found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(UpdateUserRequest request, Long id) {
        User user = findOneAndEnsureExistById(id);

        user = update(user, request);

        return BaseResponse.builder()
                .data(user)
                .message("User updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {

        repository.deleteById(id);

        return BaseResponse.builder()
                .data(null)
                .message("User deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    private User findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    private User from(CreateUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));

        Role role = userRoleService.findOneAndEnsureExistByName("USER");
        user.setRole(role);
        return user;
    }

    private User update(User user, UpdateUserRequest request) {
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));

        repository.save(user);

        return user;
    }

}
