package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.pivots.UserRole;
import com.school.ecommerceupch.entities.projections.RoleProjection;
import com.school.ecommerceupch.entities.projections.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query(value = "select users.* from users_roles " +
            "inner join users on users_roles.user_id = users.id " +
            "inner join roles on users_roles.role_id = roles.id " +
            "where users_roles.role_id = :roleId", nativeQuery = true)
    List<UserProjection> listAllUsersByRoleId(Long roleId);

    @Query(value = "select roles.* users_roles " +
            "inner join users on users_roles.user_id = users.id " +
            "inner join roles on users_roles.role_id = roles.id " +
            "where users_roles.user_id = :userId", nativeQuery = true)
    List<RoleProjection> listAllRolesByUserId(Long userId);
}
