package com.school.ecommerceupch.controllers.dtos.requests;
import com.school.ecommerceupch.entities.Order;
import com.school.ecommerceupch.entities.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderItemRequest {

    private Long id;
    private Integer quantity;
    private Product product;
    private Order order;
}
