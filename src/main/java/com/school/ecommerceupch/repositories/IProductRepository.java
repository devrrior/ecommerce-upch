package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {


    List<Product> findAllByTitleContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String keywordTitle,
                                                                                  String keywordDescription);
}
