package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateProductRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateProductRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;

import com.school.ecommerceupch.entities.Category;
import com.school.ecommerceupch.entities.Product;
import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.entities.pivots.ProductCategory;
import com.school.ecommerceupch.repositories.IProductRepository;
import com.school.ecommerceupch.services.interfaces.ICategoryService;
import com.school.ecommerceupch.services.interfaces.IFileService;
import com.school.ecommerceupch.services.interfaces.IProductCategoryService;
import com.school.ecommerceupch.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository repository;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IFileService fileService;

    @Autowired
    private IProductCategoryService productCategoryService;

    @Override
    public BaseResponse get(Long id) {
        Product product = findOneAndEnsureExists(id);
        return BaseResponse.builder()
                .data(product)
                .message("Product found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse list() {
        List<Product> products = repository.findAll();

        return BaseResponse.builder()
                .data(products)
                .message("Products found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse create(CreateProductRequest request) {
        Product product = repository.save(from(request));

        if(request.getProductCategoriesIds()!=null) {
            setProductCategoriesListToProduct(request.getProductCategoriesIds(), product);
        }

        if(!request.getFile().isEmpty()){
            String fileUrl = fileService.upload(request.getFile());
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
                .data(null)
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

    public Product findOneAndEnsureExists(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private Product from(CreateProductRequest request){
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setStock(request.getStock());
        product.setPrice(request.getPrice());


        Long user = userService.findUserById(request.getUserId());
        product.setUser(user);

        product.setOrderItems(request.getOrderItems());

        return product;
    }

    private void setProductCategoriesListToProduct(List<Long> productCategoryIds, Product product){
        List<ProductCategory> tempProductCategoryList = new ArrayList<>();

        for (Long categoryId: productCategoryIds) {
            Category category = categoryService.findOneAndEnsureExists(categoryId);
            ProductCategory newCategory = productCategoryService.create(product, category);
            tempProductCategoryList.add(newCategory);
        }

        product.setProductCategories(tempProductCategoryList);

        repository.save(product);
    }

    private Product update(Product product, UpdateProductRequest request){
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setStock(request.getStock());
        product.setPrice(request.getPrice());

        Long user = userService.findUserById(request.getUserId());
        product.setUser(user);

        if(request.getProductCategoriesIds()!=null) {
            setProductCategoriesListToProduct(request.getProductCategoriesIds(), product);
        }

        if(!request.getFile().isEmpty()){
            fileService.upload(request.getFile());
        }

        return product;
    }

}
