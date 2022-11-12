package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProductStatusRepository extends JpaRepository<ProductStatus, Long> {

    Optional<ProductStatus> findByName(String name);

    boolean existsByName(String name);
}
