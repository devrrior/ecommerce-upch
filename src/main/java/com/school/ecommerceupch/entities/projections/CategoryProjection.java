package com.school.ecommerceupch.entities.projections;

import com.school.ecommerceupch.entities.pivots.ProductCategory;

import java.util.List;

public interface CategoryProjection {

    Long getId();

    String getName();

    List<ProductCategory> getProductCategories();

}
