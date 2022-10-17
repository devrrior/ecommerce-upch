package com.school.ecommerceupch.entities;

import com.fasterxml.jackson.databind.DatabindException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(length = 40)
    private String firstName;

    @Column(length = 40)
    private String lastName;

    @Column(length = 40)
    private String role;
    
    private Date dateOfBirth;

    /*@OneToMany(mappedBy = "user")
    private List<Address> adresses;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;*/
}
