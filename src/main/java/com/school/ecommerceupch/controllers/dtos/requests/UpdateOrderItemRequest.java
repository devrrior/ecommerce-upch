package com.school.ecommerceupch.controllers.dtos.requests;

import com.school.ecommerceupch.entities.Order;
import com.school.ecommerceupch.entities.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateOrderItemRequest {

    @NotNull
    private Integer quantity;

}
