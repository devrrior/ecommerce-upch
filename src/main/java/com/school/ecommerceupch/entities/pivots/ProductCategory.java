package com.school.ecommerceupch.entities.pivots;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.school.ecommerceupch.entities.Category;
import com.school.ecommerceupch.entities.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products_categories")
@Getter
@Setter
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private Category category;

}
