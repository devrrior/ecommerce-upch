package com.school.ecommerceupch.controllers.dtos.requests;
import com.school.ecommerceupch.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAddressRequest {

    private Long id;
    private String street;
    private String zipcode;
    private String state;
    private String country;
    private User user;
}
