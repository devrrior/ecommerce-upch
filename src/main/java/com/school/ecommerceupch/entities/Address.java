package com.school.ecommerceupch.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Getter @Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true, length = 255)
    private String street;

    @Column(unique = true, length = 50)
    private String zipcode;

    @Column(unique = true, length = 100)
    private String state;

    @Column(unique = true, length = 100)
    private String country;



    /*ManyToOne
     */
    /*
    JoinColumn(name="user_id", nullable = false )
    private User user;
     */

}
