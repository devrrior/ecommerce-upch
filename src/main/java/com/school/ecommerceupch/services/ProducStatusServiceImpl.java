package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateProductStatusRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateProductStatusRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerceupch.controllers.exceptions.UniqueConstraintViolationException;
import com.school.ecommerceupch.entities.ProductStatus;
import com.school.ecommerceupch.repositories.IProductStatusRepository;
import com.school.ecommerceupch.services.interfaces.IProductStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ProducStatusServiceImpl implements IProductStatusService {

    @Autowired
    private IProductStatusRepository repository;


    @Override
    public BaseResponse get(Long id) {
        ProductStatus productStatus = findOneAndEnsureExistById(id);

        return BaseResponse.builder()
                .data(productStatus)
                .message("Product status found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }


    @Override
    public BaseResponse create(CreateProductStatusRequest request) {
        if (repository.existsByName(request.getName()))
            throw new UniqueConstraintViolationException("Name is already in use");

        ProductStatus productStatus = repository.save(from(request));

        return BaseResponse.builder()
                .data(productStatus)
                .message("Order Status created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private ProductStatus from(CreateProductStatusRequest request) {
        ProductStatus productStatus = new ProductStatus();

        productStatus.setName(request.getName());

        return productStatus;
    }

    @Override
    public BaseResponse update(Long id, UpdateProductStatusRequest request) {
        ProductStatus productStatus = findOneAndEnsureExistById(id);
        productStatus = update(productStatus, request);

        return BaseResponse.builder()
                .data(productStatus)
                .message("Product status updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    private ProductStatus update(ProductStatus productStatus, UpdateProductStatusRequest request) {
        productStatus.setName(request.getName());

        return repository.save(productStatus);
    }

    @Override
    public BaseResponse delete(Long id) {
        if (!repository.existsById(id))
            throw new ObjectNotFoundException("Product status not found");

        repository.deleteById(id);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .message("Product status deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public ProductStatus findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Product status not found"));
    }

    @Override
    public ProductStatus findOneAndEnsureExistByName(String name) {
        return repository.findByName(name).orElseThrow(()-> new ObjectNotFoundException("Product status not found"));
    }
}
