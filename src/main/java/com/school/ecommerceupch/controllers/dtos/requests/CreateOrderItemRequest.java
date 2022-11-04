package com.school.ecommerceupch.controllers.dtos.requests;

import com.school.ecommerceupch.entities.Order;
import com.school.ecommerceupch.entities.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderItemRequest {

    private Integer quantity;
    private Long productId;
    private Long orderId;
}
