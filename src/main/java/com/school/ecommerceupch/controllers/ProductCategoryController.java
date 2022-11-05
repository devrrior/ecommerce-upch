package com.school.ecommerceupch.controllers;

import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.services.interfaces.IProductCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/product-category")
public class ProductCategoryController {

    private final IProductCategoryService service;

    public ProductCategoryController(IProductCategoryService service) {
        this.service = service;
    }

    @GetMapping("product/category/{categoryId}")
    public ResponseEntity<BaseResponse> listAllProductsByCategoryId(@PathVariable Long categoryId) {
        BaseResponse response = service.listAllProductsByCategoryId(categoryId);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("category/product/{productId}")
    public ResponseEntity<BaseResponse> listAllCategoriesByProductId(@PathVariable Long productId) {
        BaseResponse response = service.listAllCategoriesByProductId(productId);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
