package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    Optional<OrderStatus> findByName(String name);
}
