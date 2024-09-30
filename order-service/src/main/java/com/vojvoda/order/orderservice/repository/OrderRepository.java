package com.vojvoda.order.orderservice.repository;

import com.vojvoda.order.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}