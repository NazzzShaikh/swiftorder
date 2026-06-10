package com.swiftorder.orderservice.repository;

import com.swiftorder.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository gives us save(), findById(), findAll() etc. for free
public interface OrderRepository extends JpaRepository<Order, Long> {
}