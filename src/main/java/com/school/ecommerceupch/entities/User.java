package com.school.ecommerceupch.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.school.ecommerceupch.entities.pivots.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
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

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Product> products;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Address> addresses;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Order> orders;
}
