package com.forj.delivery.domain.repository;

import com.forj.delivery.domain.model.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {
    Delivery save(Delivery delivery);
    Optional<Delivery> findById(UUID deliveryId);
    List<Delivery> findByDeliveryAgentId(Long deliveryAgentId);
    Optional<Delivery> findByOrderId(UUID orderId);
    Page<Delivery> findAll(Pageable pageable);
}

