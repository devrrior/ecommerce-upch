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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AddressServiceImpl implements IAddressService {

    private final IAddressRepository repository;

    public AddressServiceImpl(IAddressRepository repository) {
        this.repository = repository;
    }

    private static UserDetailsImpl getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Override
    public BaseResponse create(CreateAddressRequest request) {
        UserDetailsImpl userAuthenticated = getUserAuthenticated();

        Address address = repository.save(from(request, userAuthenticated.getUser()));

        return BaseResponse.builder()
                .data(address)
                .message("Address saved correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();

    }

    @Override
    public BaseResponse get(Long id) {

        UserDetailsImpl userAuthenticated = getUserAuthenticated();

        Address address = findOneAndEnsureExistById(id);

        if (!userAuthenticated.getUser().getId().equals(address.getUser().getId()))
            throw new AccessDeniedException();


        return BaseResponse.builder()
                .data(address)
                .message("Address found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public BaseResponse update(Long id, UpdateAddressRequest request) {
        UserDetailsImpl userAuthenticated = getUserAuthenticated();

        Address address = findOneAndEnsureExistById(id);

        if (!userAuthenticated.getUser().getId().equals(address.getUser().getId()))
            throw new AccessDeniedException();

        address = update(address, request);

        return BaseResponse.builder()
                .data(address)
                .message("Address update")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();

    }

    @Override
    public BaseResponse delete(Long id) {
        UserDetailsImpl userAuthenticated = getUserAuthenticated();

        if (!repository.existsById(id))
            throw new ObjectNotFoundException("Address not found");

        Address address = findOneAndEnsureExistById(id);

        if (!userAuthenticated.getUser().getId().equals(address.getUser().getId()))
            throw new AccessDeniedException();

        repository.deleteById(id);

        return BaseResponse.builder()
                .data(Collections.EMPTY_LIST)
                .message("Address deleted correctly ")
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
