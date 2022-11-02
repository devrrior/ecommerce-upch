package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}