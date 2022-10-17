package com.school.ecommerceupch.entities.pivots;

import com.school.ecommerceupch.entities.Category;
import com.school.ecommerceupch.entities.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products_categories")
@Getter @Setter
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Category category;

}
