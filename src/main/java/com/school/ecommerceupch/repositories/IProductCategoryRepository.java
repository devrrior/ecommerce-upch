package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.pivots.ProductCategory;
import com.school.ecommerceupch.entities.projections.CategoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.school.ecommerceupch.entities.projections.ProductProjection;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query(value = "select products.*  from products_categories " +
            "inner join products on products_categories.product_id = products.id " +
            "inner join categories on products_categories.category_id = categories.id " +
            "where products_categories.category_id = :categoryId", nativeQuery = true)
    List<ProductProjection> listAllProductsByCategoryId(Long categoryId);

    @Query(value = "select categories.* from products_categories " +
            "inner join categories on products_categories.category_id = categories.id " +
            "inner join products on products_categories.product_id = products.id " +
            "where products_categories.product_id = :productId", nativeQuery = true)
    List<CategoryProjection> listAllCategoriesByProductId(Long productId);

}
