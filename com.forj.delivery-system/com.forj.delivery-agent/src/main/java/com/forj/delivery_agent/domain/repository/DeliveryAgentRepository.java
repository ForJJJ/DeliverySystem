package com.forj.delivery_agent.domain.repository;


import com.forj.delivery_agent.domain.model.DeliveryAgent;

import java.util.Optional;

public interface DeliveryAgentRepository {

    boolean existsByDeliveryAgentId(Long deliveryAgentId);

    DeliveryAgent save(DeliveryAgent deliveryAgent);

    Optional<DeliveryAgent> findByDeliveryAgentId(Long deliveryAgentId);

}
