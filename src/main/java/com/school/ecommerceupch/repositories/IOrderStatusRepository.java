package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderStatusRepository extends JpaRepository<OrderStatus, Long>{

    @Query
    OrderStatus getOrderStatusById(Long id);
    OrderStatus findByName(String name);
    Boolean existsOrderStatusByName(String name);
}
