package com.school.ecommerceupch.controllers.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateOrderStatusRequest {

    @NotBlank
    private String name;
}
