package com.school.ecommerceupch.controllers.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class UpdateUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Date dateOfBirth;
}
