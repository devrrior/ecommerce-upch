package com.school.ecommerceupch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.school.ecommerceupch.entities.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderItemRepository extends JpaRepository <OrderItem, Long> {


}
