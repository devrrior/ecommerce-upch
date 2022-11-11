package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateProductRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateProductRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerceupch.entities.Category;
import com.school.ecommerceupch.entities.Product;
import com.school.ecommerceupch.entities.ProductStatus;
import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.entities.pivots.ProductCategory;
import com.school.ecommerceupch.repositories.IProductRepository;
import com.school.ecommerceupch.security.UserDetailsImpl;
import com.school.ecommerceupch.services.interfaces.ICategoryService;
import com.school.ecommerceupch.services.interfaces.IProductCategoryService;
import com.school.ecommerceupch.services.interfaces.IProductService;
import com.school.ecommerceupch.services.interfaces.IProductStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository repository;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductCategoryService productCategoryService;

    @Autowired
    private IProductStatusService productStatusService;

    private static UserDetailsImpl getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Override
    public BaseResponse get(Long id) {
        Product product = findOneAndEnsureExists(id);
        return BaseResponse.builder()
                .data(product)
                .message("Product found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse list(String keyword) {
        List<Product> products;

        if (keyword == null)
            products = repository.findAll();
        else products = repository.findAllByTitleContainsIgnoreCaseOrDescriptionContainsIgnoreCase(keyword, keyword);

        return BaseResponse.builder()
                .data(products)
                .message("Products found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse create(CreateProductRequest request) {

        User user = getUserAuthenticated().getUser();

        Product product = repository.save(from(request, user));

        if (request.getCategoryIds() != null)
            setProductCategoriesListToProduct(request.getCategoryIds(), product);

        return BaseResponse.builder()
                .data(product)
                .message("Product created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse update(Long id, UpdateProductRequest request) {

        Product product = findOneAndEnsureExists(id);

        product = update(product, request);

        return BaseResponse.builder()
                .data(product)
                .message("Product updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    public Product findOneAndEnsureExists(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found"));
    }

    private Product from(CreateProductRequest request, User user) {
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setStock(request.getStock());
        product.setPrice(request.getPrice());
        product.setUser(user);
        product.setImageUrl(request.getImageUrl());

        ProductStatus productStatus = productStatusService.findOneAndEnsureExistById(request.getProductStatusId());
        product.setProductStatus(productStatus);

        return product;
    }

    private void setProductCategoriesListToProduct(List<Long> productCategoryIds, Product product) {
        List<ProductCategory> tempProductCategoryList = new ArrayList<>();

        for (Long categoryId : productCategoryIds) {
            Category category = categoryService.findOneAndEnsureExists(categoryId);
            ProductCategory newCategory = productCategoryService.create(product, category);
            tempProductCategoryList.add(newCategory);
        }

        product.setProductCategories(tempProductCategoryList);

        repository.save(product);
    }

    private Product update(Product product, UpdateProductRequest request) {
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setStock(request.getStock());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());

        ProductStatus productStatus = productStatusService.findOneAndEnsureExistById(request.getProductStatusId());
        product.setProductStatus(productStatus);


        if (request.getCategoryIds() != null) {
            setProductCategoriesListToProduct(request.getCategoryIds(), product);
        }

        repository.save(product);

        return product;
    }


}
