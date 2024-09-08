package com.forj.order.domain.repostiory;

import com.forj.order.domain.model.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository{
    Order save(Order order);
    Optional<Order> findById(UUID orderId);
}
