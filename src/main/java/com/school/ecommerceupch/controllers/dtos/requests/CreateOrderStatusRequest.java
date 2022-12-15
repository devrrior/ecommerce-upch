package com.school.ecommerceupch.controllers.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CreateOrderStatusRequest {

    @NotNull
    private Long id;

    @NotBlank
    private String name;
}
