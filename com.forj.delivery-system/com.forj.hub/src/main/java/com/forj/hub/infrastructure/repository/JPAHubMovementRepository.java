package com.forj.hub.infrastructure.repository;

import com.forj.hub.domain.model.HubMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface JPAHubMovementRepository extends JpaRepository<HubMovement, UUID> {

    @Query("SELECT h FROM HubMovement h WHERE LOWER(h.route) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<HubMovement> findAllBySearch(@Param("search") String search, Pageable pageable);

    @Query("SELECT h FROM HubMovement h WHERE h.departureHubId = :departureHubId AND h.isDeleted = false")
    HubMovement findByDepartureHubId(UUID departureHubId);
}
