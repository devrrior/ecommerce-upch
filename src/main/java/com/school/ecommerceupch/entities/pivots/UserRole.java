package com.school.ecommerceupch.entities.pivots;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.school.ecommerceupch.entities.Role;
import com.school.ecommerceupch.entities.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users_roles")
@Getter
@Setter
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private Role role;


}
