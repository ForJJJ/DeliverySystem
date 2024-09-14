package com.forj.delivery_history.infrastructure.repository.hubmovement;

import com.forj.delivery_history.domain.model.hubmovement.HubMovement;
import com.forj.delivery_history.domain.repository.HubMovementRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaHubMovementRepository extends JpaRepository<HubMovement, UUID>, HubMovementRepository {
}
