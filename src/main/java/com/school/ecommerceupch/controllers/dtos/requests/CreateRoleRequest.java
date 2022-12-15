package com.school.ecommerceupch.controllers.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CreateRoleRequest {

    @NotBlank
    private String name;
}
