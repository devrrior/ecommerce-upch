package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateAddressRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateAddressRequest;
import com.school.ecommerceupch.controllers.dtos.responses.GetAddressResponse;
import com.school.ecommerceupch.entities.Address;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;

import java.util.List;


public interface IAddressService {

   BaseResponse create (CreateAddressRequest request);
   BaseResponse get (Long id);
   BaseResponse update (Long id, UpdateAddressRequest request);
   BaseResponse delete (Long id);

   Address findOneAndEnsureExistById(Long id);

}
