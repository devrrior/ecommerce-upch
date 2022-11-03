package com.school.ecommerceupch.entities;

import com.school.ecommerceupch.entities.pivots.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Setter @Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<ProductCategory> productCategories;
}
