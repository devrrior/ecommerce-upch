package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateCategoryRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateCategoryRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Category;

public interface ICategoryService {

    BaseResponse list();

    BaseResponse get(Long id);

    BaseResponse create(CreateCategoryRequest request);

    BaseResponse update(Long id, UpdateCategoryRequest request);

    BaseResponse delete(Long id);

    Category findOneAndEnsureExistById(Long id);

    Category findOneAndEnsureExistByName(String name);

}
