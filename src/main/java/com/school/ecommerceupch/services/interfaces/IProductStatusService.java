package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateProductStatusRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateProductStatusRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.ProductStatus;

public interface IProductStatusService {

    BaseResponse list();

    ProductStatus findOneAndEnsureExistById(Long id);

}
