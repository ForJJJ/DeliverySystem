package com.forj.order.domain.repostiory;

import com.forj.order.domain.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
