package com.school.ecommerceupch.entities.projections;

import com.school.ecommerceupch.entities.pivots.UserRole;

import java.util.List;

public interface RoleProjection {

    Long getId();

    String getName();

    List<UserRole> getUserRoles();
}
