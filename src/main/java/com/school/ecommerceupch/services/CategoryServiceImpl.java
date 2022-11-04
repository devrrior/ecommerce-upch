package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateCategoryRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateCategoryRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Category;
import com.school.ecommerceupch.repositories.ICategoryRepository;
import com.school.ecommerceupch.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository repository;

    @Override
    public BaseResponse list() {
        List<Category> categories = repository.findAll();

        return BaseResponse.builder()
                .data(categories)
                .message("Categories found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse get(Long id) {
        Category category = findOneAndEnsureExists(id);
        return BaseResponse.builder()
                .data(category)
                .message("Category found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse create(CreateCategoryRequest request) {
        Category category = from(request);
        return BaseResponse.builder()
                .data(repository.save(category))
                .message("Category created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse update(Long id, UpdateCategoryRequest request) {
        Category category = findOneAndEnsureExists(id);
        category = update(category, request);

        return BaseResponse.builder()
                .data(repository.save(category))
                .message("Category updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public BaseResponse delete(Long id) {
        Category category = findOneAndEnsureExists(id);
        repository.delete(category);

        return BaseResponse.builder()
                .data(null)
                .message("Category deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public Category findOneAndEnsureExists(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    private Category from(CreateCategoryRequest request){
        Category category = new Category();
        category.setName(request.getName());

        return category;
    }

    private Category update(Category category, UpdateCategoryRequest request){
        category.setName(request.getName());
        return category;
    }
}
