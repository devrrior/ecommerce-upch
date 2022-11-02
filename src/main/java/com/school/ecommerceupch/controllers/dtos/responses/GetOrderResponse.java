package com.school.ecommerceupch.controllers.dtos.responses;

import com.school.ecommerceupch.entities.OrderStatus;
import com.school.ecommerceupch.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter  @Setter
public class GetOrderResponse {
    private Long Id;

    private String status;

    private OrderStatus orderStatus;

    private User user;
}
