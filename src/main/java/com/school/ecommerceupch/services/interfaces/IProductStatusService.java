package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateProductStatusRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateProductStatusRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.ProductStatus;

public interface IProductStatusService {

    BaseResponse get(Long id);

    BaseResponse create(CreateProductStatusRequest request);

    BaseResponse update(Long id, UpdateProductStatusRequest request);

    BaseResponse delete(Long id);

    ProductStatus findOneAndEnsureExistById(Long id);

    ProductStatus findOneAndEnsureExistByName(String name);
}
