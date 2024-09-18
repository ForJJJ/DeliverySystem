package com.forj.hub.domain.repository;

import com.forj.hub.domain.model.HubMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HubMovementRepository {
    HubMovement findById(UUID hubMovementId);

    HubMovement save(HubMovement hubMovement);

    Page<HubMovement> findAllBySearch(String search, Pageable pageable);

    HubMovement findByDepartureHubId(UUID departureHubId);
}
