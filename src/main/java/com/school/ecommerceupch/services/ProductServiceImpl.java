package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateProductRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateProductRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerceupch.entities.Category;
import com.school.ecommerceupch.entities.Product;
import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.entities.pivots.ProductCategory;
import com.school.ecommerceupch.repositories.IProductRepository;
import com.school.ecommerceupch.security.UserDetailsImpl;
import com.school.ecommerceupch.services.interfaces.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository repository;

    private final ICategoryService categoryService;

    private final IFileService fileService;

    private final IProductCategoryService productCategoryService;

    public ProductServiceImpl(IProductRepository repository, ICategoryService categoryService,
                              @Qualifier("cloudinary") IFileService fileService,
                              IProductCategoryService productCategoryService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.fileService = fileService;
        this.productCategoryService = productCategoryService;
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
    public BaseResponse list() {
        List<Product> products = repository.findAll();

        return BaseResponse.builder()
                .data(products)
                .message("Products found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse create(CreateProductRequest request) {

        UserDetailsImpl userAuthenticated = getUserAuthenticated();

        Product product = repository.save(from(request, userAuthenticated.getUser()));

        if (request.getCategoryIds() != null)
            setProductCategoriesListToProduct(request.getCategoryIds(), product);

        if (!request.getImage().isEmpty()) {
            String fileUrl = fileService.upload(request.getImage());
            updateProductImage(fileUrl, product.getId());
        }

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

    @Override
    public BaseResponse delete(Long id) {
        Product product = findOneAndEnsureExists(id);
        repository.delete(product);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .message("Product deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public void updateProductImage(String productImageUrl, Long idProduct) {
        Product product = findOneAndEnsureExists(idProduct);
        product.setImageUrl(productImageUrl);
        repository.save(product);
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


        if (request.getCategoryIds() != null) {
            setProductCategoriesListToProduct(request.getCategoryIds(), product);
        }

        if (!request.getImage().isEmpty()) {
            fileService.delete(product.getImageUrl());
            String newImageUrl = fileService.upload(request.getImage());
            product.setImageUrl(newImageUrl);
        }

        repository.save(product);

        return product;
    }

    private static UserDetailsImpl getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }


}
