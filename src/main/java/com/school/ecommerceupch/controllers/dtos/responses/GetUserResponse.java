package com.school.ecommerceupch.controllers.dtos.responses;

import com.school.ecommerceupch.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class GetUserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private UserRole userRole;
}
