package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Category;
import com.school.ecommerceupch.entities.Product;
import com.school.ecommerceupch.entities.pivots.ProductCategory;
import com.school.ecommerceupch.entities.projections.CategoryProjection;
import com.school.ecommerceupch.entities.projections.ProductProjection;
import com.school.ecommerceupch.repositories.IProductCategoryRepository;
import com.school.ecommerceupch.services.interfaces.ICategoryService;
import com.school.ecommerceupch.services.interfaces.IProductCategoryService;
import com.school.ecommerceupch.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {

    @Autowired
    private IProductCategoryRepository repository;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Override
    public ProductCategory create(Product product, Category category) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProduct(product);
        productCategory.setCategory(category);
        return repository.save(productCategory);
    }

    @Override
    public BaseResponse listAllProductsByCategoryName(String categoryName) {

        categoryService.findOneAndEnsureExistByName(categoryName);

        List<ProductProjection> productProjections = repository.listAllProductsByCategoryName(categoryName);
        List<Product> products = productProjections.stream()
                .map(this::from)
                .collect(Collectors.toList());

        return BaseResponse.builder()
                .data(products)
                .message("Products found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse listAllCategoriesByProductId(Long productId) {

        productService.findOneAndEnsureExists(productId);

        List<CategoryProjection> categoryProjections = repository.listAllCategoriesByProductId(productId);
        List<Category> categories = categoryProjections.stream()
                .map(this::toCategoryFrom)
                .collect(Collectors.toList());

        return BaseResponse.builder()
                .data(categories)
                .message("Categories found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    private Product from(ProductProjection productProjection) {
        Product product = new Product();
        product.setId(productProjection.getId());
        product.setTitle(productProjection.getTitle());
        product.setDescription(productProjection.getDescription());
        product.setImageUrl(productProjection.getImage_url());
        product.setStock(productProjection.getStock());
        product.setPrice(productProjection.getPrice());
        product.setUser(productProjection.getUser());

        return product;
    }

    private Category toCategoryFrom(CategoryProjection categoryProjection) {
        Category category = new Category();
        category.setId(categoryProjection.getId());
        category.setName(categoryProjection.getName());
        category.setProductCategories(categoryProjection.getProductCategories());
        return category;
    }
}
