package com.school.ecommerceupch.entities.projections;

import com.school.ecommerceupch.entities.User;

public interface ProductProjection {

    Long getId();

    String getTitle();

    String getDescription();

    String getImage_url();

    Integer getStock();

    Float getPrice();

    User getUser();

}
