package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateProductRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateProductRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.OrderItem;
import com.school.ecommerceupch.entities.Product;

public interface IProductService {

    BaseResponse get(Long id);

    BaseResponse list(String keyword);

    BaseResponse create(CreateProductRequest request);

    BaseResponse update(Long id, UpdateProductRequest request);

    Product findOneAndEnsureExists(Long id);

    void updateStock(Product product, Integer newStock);

}
