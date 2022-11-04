package com.school.ecommerceupch.config;

import com.school.ecommerceupch.entities.OrderStatus;
import com.school.ecommerceupch.entities.Role;
import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.entities.pivots.UserRole;
import com.school.ecommerceupch.repositories.IOrderStatusRepository;
import com.school.ecommerceupch.repositories.IRoleRepository;
import com.school.ecommerceupch.repositories.IUserRepository;
import com.school.ecommerceupch.services.RoleServiceImpl;
import com.school.ecommerceupch.services.interfaces.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    private final IUserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final IRoleRepository roleRepository;
    private final IUserRoleService userRoleService;
    private final IOrderStatusRepository orderStatusRepository;
    private final PasswordEncoder passwordEncoder;
    boolean alreadySetup = false;

    @Autowired
    public SetupDataLoader(IUserRepository userRepository, RoleServiceImpl roleService, IRoleRepository roleRepository, IUserRoleService userRoleService, IOrderStatusRepository orderStatusRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.userRoleService = userRoleService;
        this.orderStatusRepository = orderStatusRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_ADMIN");

        createOrderStatusIfNotFound("IN_PROGRESS");
        createOrderStatusIfNotFound("PENDING");
        createOrderStatusIfNotFound("DELIVERED");


        Role adminRole = roleService.findOneAndEnsureExistByName("ROLE_ADMIN");


        User user = new User();

        user.setEmail("test@test.com");
        user.setPassword(passwordEncoder.encode("test"));
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setDateOfBirth(new Date());

        if (userRepository.existsByEmail(user.getEmail())) return;


        userRepository.save(user);

        List<UserRole> userRoleList = new ArrayList<>();
        UserRole userRole = userRoleService.create(user, adminRole);

        userRoleList.add(userRole);

        user.setUserRoles(userRoleList);
        userRepository.save(user);


        alreadySetup = true;
    }

    @Transactional
    void createRoleIfNotFound(String name) {

        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isEmpty()) {
            Role role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
    }

    @Transactional
    void createOrderStatusIfNotFound(String name) {
        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findByName(name);
        if (optionalOrderStatus.isEmpty()) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setName(name);
            orderStatusRepository.save(orderStatus);
        }

    }


}