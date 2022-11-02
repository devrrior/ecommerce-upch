package com.school.ecommerceupch.repositories;

import com.fasterxml.jackson.databind.ObjectReader;
import com.school.ecommerceupch.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    @Query
    Order getOrderById(Long Id);
    Boolean existsOrderByStatus(String Status);

    Order findByStatus(String Status);
}
