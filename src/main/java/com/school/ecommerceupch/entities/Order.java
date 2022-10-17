package com.school.ecommerceupch.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private User user;
    @Column(length = 10)
    private String status;

    /*
    @ManyToOne
    private User user;

    @OneToMany
    private List<OrderItem> orderItems;
     */

}
