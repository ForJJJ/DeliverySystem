package com.forj.delivery.domain.repository;

import com.forj.delivery.domain.model.Delivery;

public interface DeliveryRepository {
    Delivery save(Delivery delivery);
}
