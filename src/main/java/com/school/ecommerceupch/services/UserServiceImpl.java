package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateUserRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateUserRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.dtos.responses.UserResponse;
import com.school.ecommerceupch.controllers.exceptions.AccessDeniedException;
import com.school.ecommerceupch.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerceupch.controllers.exceptions.UniqueConstraintViolationException;
import com.school.ecommerceupch.entities.Role;
import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.entities.pivots.UserRole;
import com.school.ecommerceupch.repositories.IUserRepository;
import com.school.ecommerceupch.security.UserDetailsImpl;
import com.school.ecommerceupch.services.interfaces.IRoleService;
import com.school.ecommerceupch.services.interfaces.IUserRoleService;
import com.school.ecommerceupch.services.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository repository;
    private final IRoleService roleService;
    private final IUserRoleService userRoleService;

    public UserServiceImpl(IUserRepository repository, IRoleService roleService, IUserRoleService userRoleService) {
        this.repository = repository;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    private static UserDetailsImpl getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails;
    }

    @Override
    public BaseResponse get(Long id) {

        UserDetailsImpl userDetails = getUserAuthenticated();

        if (!userDetails.getId().equals(id))
            throw new AccessDeniedException();

        User user = findOneAndEnsureExistById(id);

        return BaseResponse.builder()
                .data(from(user))
                .message("User found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse create(CreateUserRequest request) {

        if (repository.existsByEmail(request.getEmail()))
            throw new UniqueConstraintViolationException("Email is already in use");

        User user = repository.save(from(request));

        Role defaultRole = roleService.findOneAndEnsureExistByName("ROLE_USER");

        user = addRoleToUser(defaultRole, user);

        return BaseResponse.builder()
                .data(from(user))
                .message("User created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public BaseResponse update(UpdateUserRequest request, Long id) {

        UserDetailsImpl userDetails = getUserAuthenticated();

        if (!userDetails.getId().equals(id))
            throw new AccessDeniedException();

        User user = findOneAndEnsureExistById(id);

        user = update(user, request);

        return BaseResponse.builder()
                .data(from(user))
                .message("User updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse delete(Long id) {

        UserDetailsImpl userDetails = getUserAuthenticated();

        if (!userDetails.getId().equals(id))
            throw new AccessDeniedException();

        if (!repository.existsById(id))
            throw new ObjectNotFoundException("User not found");

        repository.deleteById(id);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .message("User deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public User findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

    private User addRoleToUser(Role defaultRole, User user) {
        UserRole userRole = userRoleService.create(user, defaultRole);

        List<UserRole> userRoleList = new ArrayList<>();
        userRoleList.add(userRole);

        user.setUserRoles(userRoleList);

        user = repository.save(user);

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

    private User from(CreateUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));

        return user;
    }

    private UserResponse from(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setDateOfBirth(user.getDateOfBirth());

        return userResponse;
    }
}
