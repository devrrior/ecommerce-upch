package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Category;
import com.school.ecommerceupch.entities.Product;
import com.school.ecommerceupch.entities.pivots.ProductCategory;

public interface IProductCategoryService {

    ProductCategory create(Product product, Category category);

    BaseResponse listAllProductsByCategoryId(Long categoryId);

    BaseResponse listAllCategoriesByProductId(Long productId);

}
