package com.forj.delivery_agent.infrastructure.repository;

import com.forj.delivery_agent.domain.model.DeliveryAgent;
import com.forj.delivery_agent.domain.repository.DeliveryAgentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDeliveryAgentRepository extends JpaRepository<DeliveryAgent, UUID>, DeliveryAgentRepository {

}
