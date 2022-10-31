package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateUserRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateUserRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.dtos.responses.GetUserResponse;
import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.entities.UserRole;
import com.school.ecommerceupch.repositories.IUserRepository;
import com.school.ecommerceupch.services.interfaces.IUserRoleService;
import com.school.ecommerceupch.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private IUserRoleService roleService;

    @Override
    public BaseResponse create(CreateUserRequest request) {
        if (repository.existsUserByEmail(request.getEmail())) {
            return BaseResponse.builder()
                    .data(null)
                    .message("Email is taken")
                    .success(Boolean.FALSE)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
        User user = toUser(request);
        return BaseResponse.builder()
                .data(toGetUserResponse(repository.save(user)))
                .message("User created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse get(Long id) {
        if (!repository.existsUserById(id)) {
            return userDoesntExist();
        }
        return BaseResponse.builder()
                .data(toGetUserResponse(repository.getUserById(id)))
                .message("User exists")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(UpdateUserRequest request, Long id) {
        if (!repository.existsUserById(id)) {
            return userDoesntExist();
        }
        if (repository.existsUserByEmail(request.getEmail())) {
            return BaseResponse.builder()
                    .data(null)
                    .message("Email is taken")
                    .success(Boolean.FALSE)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
        User user = toUserUpdate(request, id);
        return BaseResponse.builder()
                .data(toGetUserResponse(repository.save(user)))
                .message("User updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {
        if(!repository.existsUserById(id)) {
            return userDoesntExist();
        }

        repository.deleteById(id);

        return BaseResponse.builder()
                .data(null)
                .message("User deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private BaseResponse userDoesntExist() {
        return BaseResponse.builder()
                .data(null)
                .message("User doesn't exists")
                .success(Boolean.FALSE)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
    private User toUser(CreateUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));

        UserRole role = roleService.findByName("USER");
        user.setUserRole(role);
        return user;
    }

    private User toUserUpdate(UpdateUserRequest request, Long id) {
        User user = repository.getUserById(id);
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));

        return user;
    }

    private GetUserResponse toGetUserResponse(User user) {
        GetUserResponse response = new GetUserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setDateOfBirth(user.getDateOfBirth());
        response.setUserRole(user.getUserRole());
        return response;
    }
}
