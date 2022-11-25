package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> getOneByOrderStatus_NameAndUser_Id(String name, Long userId);
}
