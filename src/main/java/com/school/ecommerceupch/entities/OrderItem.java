package com.school.ecommerceupch.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(mappedBy = "orderItem")
//    private Product product;

    private Integer quantity;

//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;

}
