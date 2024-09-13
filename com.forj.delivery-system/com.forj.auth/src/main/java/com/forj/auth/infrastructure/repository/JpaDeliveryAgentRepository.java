package com.forj.auth.infrastructure.repository;

import com.forj.auth.domain.model.DeliveryAgent;
import com.forj.auth.domain.repository.DeliveryAgentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDeliveryAgentRepository extends JpaRepository<DeliveryAgent, UUID>, DeliveryAgentRepository {

}
