package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByName(String name);
}
