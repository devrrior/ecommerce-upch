package com.school.ecommerceupch.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 255)
    private String street;

    @Column(length = 50)
    private String zipcode;

    @Column(length = 100)
    private String state;

    @Column(length = 100)
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
