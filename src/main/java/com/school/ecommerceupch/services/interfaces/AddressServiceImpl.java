package com.school.ecommerceupch.services.interfaces;

import com.school.ecommerceupch.controllers.dtos.requests.CreateAddressRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateAddressRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.dtos.responses.GetAddressResponse;
import com.school.ecommerceupch.entities.Address;
import com.school.ecommerceupch.repositories.IAddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private IAddressRepository repository;


    @Override
    public BaseResponse create(CreateAddressRequest request) {
        Address address = repository.save(from(request));

        return BaseResponse.builder()
                .data(address)
                .message("Address saved correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();

    }

    @Override
    public BaseResponse get(Long id) {
        Address address = findOneAndEnsureExistById(id);

        return BaseResponse.builder()
                .data(address)
                .message("Address found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateAddressRequest request) {
            Address address = findOneAndEnsureExistById(id);
            address = update(address,request);

            return BaseResponse.builder()
                    .data(address)
                .message("Address update")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();

    }

    @Override
    public BaseResponse delete(Long id) {
        repository.deleteById(id);
        return BaseResponse.builder()
                .data(null)
                .message("Address deleted correctly ")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }
    @Override
    public Address findOneAndEnsureExistById(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("The address does not exist"));
                
    }
    private Address from(CreateAddressRequest request){
        Address address = new Address();  //street,zip,state,country, userid
        address.setStreet(request.getStreet());
        address.setZipcode(request.getZipcode());
        address.setCountry(request.getCountry());
        return address;
    }

    private Address update(Address address, UpdateAddressRequest request){
        address.setStreet(request.getStreet());
        address.setZipcode(request.getZipcode());
        address.setCountry(request.getCountry());
        repository.save(address);
        return address;
    }

}
