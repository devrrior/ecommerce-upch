package com.school.ecommerceupch.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.school.ecommerceupch.entities.pivots.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Setter @Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String title;

    private String description;

    private String imageUrl;

    private Integer stock;

    private Float price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference(value = "product")
    private List<ProductCategory> productCategories;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference(value = "product")
    private List<OrderItem> orderItems;

}
