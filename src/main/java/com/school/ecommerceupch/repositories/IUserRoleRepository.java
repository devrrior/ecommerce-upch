package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("SELECT r FROM UserRole r WHERE r.name = ?1")
    UserRole findByName(String name);
    UserRole getUserRoleById(Long id);
    Boolean existsUserRoleByName(String name);
}
