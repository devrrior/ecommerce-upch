package com.school.ecommerceupch.security;

import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findOneByEmail(email).orElseThrow(() -> new RuntimeException("User: " + email + " not found"));

        return new UserDetailsImpl(user);
    }
}