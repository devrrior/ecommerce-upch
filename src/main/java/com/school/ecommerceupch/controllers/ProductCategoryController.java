package com.school.ecommerceupch.controllers;

import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.services.interfaces.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/product-category")
public class ProductCategoryController {

    @Autowired
    private IProductCategoryService service;

    @GetMapping("product/category/{categoryName}")
    public ResponseEntity<BaseResponse> listAllProductsByCategoryName(@PathVariable String categoryName) {
        BaseResponse response = service.listAllProductsByCategoryName(categoryName);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("category/product/{productId}")
    public ResponseEntity<BaseResponse> listAllCategoriesByProductId(@PathVariable Long productId) {
        BaseResponse response = service.listAllCategoriesByProductId(productId);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
