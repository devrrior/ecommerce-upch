package com.school.ecommerceupch.controllers;

import com.school.ecommerceupch.controllers.dtos.requests.CreateCategoryRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateCategoryRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.services.interfaces.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    private final ICategoryService service;

    public CategoryController(ICategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<BaseResponse> list() {
        BaseResponse response = service.list();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable Long id) {
        BaseResponse response = service.get(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreateCategoryRequest request) {
        BaseResponse response = service.create(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable Long id, @RequestBody UpdateCategoryRequest request) {
        BaseResponse response = service.update(id, request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id) {
        BaseResponse response = service.delete(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
