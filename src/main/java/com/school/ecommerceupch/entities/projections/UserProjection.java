package com.school.ecommerceupch.entities.projections;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.school.ecommerceupch.entities.Address;
import com.school.ecommerceupch.entities.Product;
import com.school.ecommerceupch.entities.pivots.UserRole;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public interface UserProjection {

    Long getId();

    String getEmail();

    String getPassword();

    String getFirstName();

    String getLastName();

    Date getDateOfBirth();

    List<UserRole> getUserRoles();

    List<Product> getProducts();

    List<Address> getAddresses();
}
