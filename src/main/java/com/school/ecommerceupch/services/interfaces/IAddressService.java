package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateAddressRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateAddressRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.entities.Address;


public interface IAddressService {

    BaseResponse create(CreateAddressRequest request);

    BaseResponse get(Long id);

    BaseResponse list(Long userId);

    BaseResponse update(Long id, UpdateAddressRequest request);

    BaseResponse delete(Long id);

    Address findOneAndEnsureExistById(Long id);

}
