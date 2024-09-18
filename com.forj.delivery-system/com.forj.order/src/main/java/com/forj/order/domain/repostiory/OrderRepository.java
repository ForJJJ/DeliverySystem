package com.forj.order.domain.repostiory;

import com.forj.order.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository{
    Order save(Order order);
    Optional<Order> findById(UUID orderId);
    Page<Order> findAll(Pageable pageable);
}
