package com.forj.delivery.domain.repository;

import com.forj.delivery.domain.model.delivery.Delivery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {
    Delivery save(Delivery delivery);
    Optional<Delivery> findById(UUID deliveryId);
    List<Delivery> findByDeliveryAgentId(UUID deliveryAgentId);
}

