package com.school.ecommerceupch.controllers.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateOrderItemRequest {

    @NotNull
    private Integer quantity;

    @NotNull
    private Long productId;

}
