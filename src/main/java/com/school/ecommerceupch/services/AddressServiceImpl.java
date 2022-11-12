package com.school.ecommerceupch.services;

import com.school.ecommerceupch.controllers.dtos.requests.CreateAddressRequest;
import com.school.ecommerceupch.controllers.dtos.requests.UpdateAddressRequest;
import com.school.ecommerceupch.controllers.dtos.responses.BaseResponse;
import com.school.ecommerceupch.controllers.exceptions.AccessDeniedException;
import com.school.ecommerceupch.controllers.exceptions.ObjectNotFoundException;
import com.school.ecommerceupch.entities.Address;
import com.school.ecommerceupch.entities.User;
import com.school.ecommerceupch.repositories.IAddressRepository;
import com.school.ecommerceupch.security.UserDetailsImpl;
import com.school.ecommerceupch.services.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private IAddressRepository repository;

    private static UserDetailsImpl getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Override
    public BaseResponse create(CreateAddressRequest request) {

        User user = getUserAuthenticated().getUser();

        Address address = repository.save(from(request, user));

        return BaseResponse.builder()
                .data(address)
                .message("Address saved correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();

    }

    @Override
    public BaseResponse get(Long id) {

        User user = getUserAuthenticated().getUser();

        Address address = findOneAndEnsureExistById(id);

        if (!user.getId().equals(address.getUser().getId()))
            throw new AccessDeniedException();


        return BaseResponse.builder()
                .data(address)
                .message("Address found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse list(Long userId) {

        List<Address> addresses = new ArrayList<>();

        if (userId != null) {
            addresses = repository.findAllByUserId(userId);
        } else {
            addresses = repository.findAll();
        }

        return BaseResponse.builder()
                .data(addresses)
                .message("Addresses found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateAddressRequest request) {

        User user = getUserAuthenticated().getUser();

        Address address = findOneAndEnsureExistById(id);

        if (!user.getId().equals(address.getUser().getId()))
            throw new AccessDeniedException();

        address = update(address, request);

        return BaseResponse.builder()
                .data(address)
                .message("Address updated correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();

    }

    @Override
    public BaseResponse delete(Long id) {

        if (!repository.existsById(id))
            throw new ObjectNotFoundException("Address not found");

        Address address = findOneAndEnsureExistById(id);
        User user = getUserAuthenticated().getUser();

        if (!user.getId().equals(address.getUser().getId()))
            throw new AccessDeniedException();

        repository.deleteById(id);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .message("Address deleted correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    @Override
    public Address findOneAndEnsureExistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("The address does not exist"));

    }

    private Address from(CreateAddressRequest request, User user) {
        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setZipcode(request.getZipcode());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setUser(user);

        repository.save(address);

        return address;
    }

    private Address update(Address address, UpdateAddressRequest request) {
        address.setStreet(request.getStreet());
        address.setZipcode(request.getZipcode());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        repository.save(address);
        return address;
    }

}
