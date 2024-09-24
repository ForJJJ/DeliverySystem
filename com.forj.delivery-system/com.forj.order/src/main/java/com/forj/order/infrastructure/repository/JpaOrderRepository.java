package com.forj.order.infrastructure.repository;

import com.forj.order.domain.model.Order;
import com.forj.order.domain.repostiory.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<Order, UUID> , OrderRepository {
}
