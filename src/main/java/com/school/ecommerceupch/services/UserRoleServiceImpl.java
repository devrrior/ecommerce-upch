package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Role;
import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.entities.pivots.UserRole;
import com.school.ecommerceupch.entities.projections.RoleProjection;
import com.school.ecommerceupch.entities.projections.UserProjection;
import com.school.ecommerceupch.repositories.IUserRoleRepository;
import com.school.ecommerceupch.services.interfaces.IUserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements IUserRoleService {

    private final IUserRoleRepository repository;


    public UserRoleServiceImpl(IUserRoleRepository repository) {
        this.repository = repository;

    }

    @Override
    public BaseResponse listAllUsersByRoleId(Long id) {

        List<UserProjection> userProjections = repository.listAllUsersByRoleId(id);
        List<User> users = userProjections.stream()
                .map(this::from).collect(Collectors.toList());

        return BaseResponse.builder()
                .data(users)
                .message("User list by role id")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse listAllRolesByUserId(Long id) {

        List<Role> roles = getAllRolesByUserId(id);

        return BaseResponse.builder()
                .data(roles)
                .message("Role list by user id")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public List<Role> getAllRolesByUserId(Long id) {
        List<RoleProjection> roleProjections = repository.listAllRolesByUserId(id);

        return roleProjections.stream()
                .map(this::from).collect(Collectors.toList());
    }

    @Override
    public UserRole create(User user, Role role) {
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);

        repository.save(userRole);

        return userRole;
    }

    private User from(UserProjection userProjection) {
        User user = new User();
        user.setId(userProjection.getId());
        user.setEmail(userProjection.getEmail());
        user.setPassword(userProjection.getPassword());
        user.setFirstName(userProjection.getFirstName());
        user.setLastName(userProjection.getLastName());
        user.setDateOfBirth(userProjection.getDateOfBirth());
        user.setUserRoles(userProjection.getUserRoles());
        user.setProducts(userProjection.getProducts());
        user.setAddresses(userProjection.getAddresses());

        return user;
    }

    private Role from(RoleProjection roleProjection) {
        Role role = new Role();
        role.setId(roleProjection.getId());
        role.setName(roleProjection.getName());
        role.setUserRoles(roleProjection.getUserRoles());

        return role;
    }
}
