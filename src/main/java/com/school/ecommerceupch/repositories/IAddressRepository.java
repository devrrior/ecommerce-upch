package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findById(Long id);


}
