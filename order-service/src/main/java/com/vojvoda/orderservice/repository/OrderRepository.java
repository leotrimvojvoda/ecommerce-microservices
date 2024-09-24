package com.vojvoda.orderservice.repository;

import com.vojvoda.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}