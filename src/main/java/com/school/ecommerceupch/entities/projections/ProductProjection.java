package com.school.ecommerceupch.entities.projections;

import com.school.ecommerceupch.entities.OrderItem;
import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.entities.pivots.ProductCategory;

import java.util.List;

public interface ProductProjection {

    Long getId();

    String getTitle();

    String getDescription();

    String getImageUrl();

    Integer getStock();

    Float getPrice();

    User getUser();

    List<ProductCategory> getProductCategories();

    List<OrderItem> getOrderItems();

}
