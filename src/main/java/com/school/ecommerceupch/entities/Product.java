package com.school.ecommerceupch.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.school.ecommerceupch.entities.pivots.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Setter
@Getter
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
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductCategory> productCategories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "productStatus_id")
    @JsonBackReference
    private ProductStatus productStatus;

}
