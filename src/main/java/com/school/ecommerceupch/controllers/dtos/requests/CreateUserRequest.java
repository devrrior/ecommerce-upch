package com.school.ecommerceupch.controllers.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
public class CreateUserRequest {
    @Email
    private String email;

    @NotBlank
    @NotNull
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    @NotBlank
    @NotNull
    private String password;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
}
