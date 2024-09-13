package com.forj.auth.domain.repository;

import com.forj.auth.domain.model.DeliveryAgent;

import java.util.Optional;

public interface DeliveryAgentRepository {

    boolean existsByDeliveryAgentId(Long deliveryAgentId);

    DeliveryAgent save(DeliveryAgent deliveryAgent);

    Optional<DeliveryAgent> findByDeliveryAgentId(Long deliveryAgentId);

}
