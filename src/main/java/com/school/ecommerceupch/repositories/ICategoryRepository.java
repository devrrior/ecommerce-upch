package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
