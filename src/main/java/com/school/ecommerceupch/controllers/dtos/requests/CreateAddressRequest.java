package com.school.ecommerceupch.controllers.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateAddressRequest {

    @NotBlank
    private String street;

    @NotBlank
    private String zipcode;

    @NotBlank
    private String state;

    @NotBlank
    private String country;
}
