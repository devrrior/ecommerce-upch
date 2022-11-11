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
import java.util.List;

@Service
public class ProducStatusServiceImpl implements IProductStatusService {

    @Autowired
    private IProductStatusRepository repository;

    @Override
    public BaseResponse list() {
        List<ProductStatus> productStatus = repository.findAll();

        return BaseResponse.builder()
                .data(productStatus)
                .message("Product status found correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ProductStatus findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Product status not found"));
    }

}
