package com.school.ecommerceupch.controllers;

import com.school.ecommerceupch.controllers.dtos.requests.CreateProductRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateProductRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.services.interfaces.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private final IProductService service;

    public ProductController(IProductService service) {
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
    public ResponseEntity<BaseResponse> create(@ModelAttribute CreateProductRequest request) {
        BaseResponse response = service.create(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable Long id, @ModelAttribute UpdateProductRequest request) {
        BaseResponse response = service.update(id, request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id) {
        BaseResponse response = service.delete(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
