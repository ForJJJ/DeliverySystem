package com.forj.delivery.domain.repository;

import com.forj.delivery.domain.model.Delivery;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {
    Delivery save(Delivery delivery);
    Optional<Delivery> findById(UUID deliveryId);
}
