package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.AuthenticationRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.security.UserDetailsImpl;
import com.school.ecommerceupch.services.interfaces.IAuthService;
import com.school.ecommerceupch.utils.JWTUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;

    private final JWTUtils jwtUtils;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public BaseResponse authenticate(AuthenticationRequest request) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                );

        Map<String, Object> payload = new HashMap<>();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String email = userDetails.getUsername();

        String fullName = userDetails.getUser().getFirstName() + " " + userDetails.getUser().getLastName();

        Collection<String> roles = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        payload.put("userId", userDetails.getUser().getId());
        payload.put("fullName", fullName);
        payload.put("roles", roles);

        String token = jwtUtils.generateToken(email, payload);

        Map<String, String> data = new HashMap<>();
        data.put("accessToken", token);

        return BaseResponse.builder()
                .data(data)
                .message("Successful authentication")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

}
