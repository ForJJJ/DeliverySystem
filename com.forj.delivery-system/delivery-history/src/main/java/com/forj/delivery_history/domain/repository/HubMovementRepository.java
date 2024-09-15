package com.forj.delivery_history.domain.repository;

import com.forj.delivery_history.domain.model.hubmovement.HubMovement;

import java.util.Optional;
import java.util.UUID;

public interface HubMovementRepository {
    Optional<HubMovement> findById(UUID departureHub);
}
