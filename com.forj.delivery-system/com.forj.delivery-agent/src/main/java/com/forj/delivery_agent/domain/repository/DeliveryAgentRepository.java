package com.forj.delivery_agent.domain.repository;


import com.forj.delivery_agent.domain.model.DeliveryAgent;
import com.forj.delivery_agent.domain.model.DeliveryAgentRole;

import java.util.List;
import java.util.Optional;

public interface DeliveryAgentRepository {

    boolean existsByDeliveryAgentId(Long deliveryAgentId);

    DeliveryAgent save(DeliveryAgent deliveryAgent);

    Optional<DeliveryAgent> findByDeliveryAgentId(Long deliveryAgentId);

    List<DeliveryAgent> findByAgentRole(DeliveryAgentRole role);

}
