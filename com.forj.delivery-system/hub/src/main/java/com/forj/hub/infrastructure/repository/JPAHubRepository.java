package com.forj.hub.infrastructure.repository;

import com.forj.hub.domain.model.Hub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JPAHubRepository extends JpaRepository<Hub, UUID>{
    @Query("SELECT h FROM Hub h WHERE h.id = :hubId AND h.isDeleted = false")
    Optional<Hub> findByIdAndIsDeletedFalse(UUID hubId);
}
