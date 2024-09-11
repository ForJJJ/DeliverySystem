package com.forj.hub.infrastructure.repository;

import com.forj.hub.domain.model.HubMovement;
import com.forj.hub.domain.repository.HubMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubMovementRepositoryImpl implements HubMovementRepository {

    private final JPAHubMovementRepository jpaHubMovementRepository;
    @Override
    public HubMovement findById(UUID hubMovementId) {
        return jpaHubMovementRepository.findById(hubMovementId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public HubMovement save(HubMovement hubMovement) {
        return jpaHubMovementRepository.save(hubMovement);
    }

    @Override
    public Page<HubMovement> findAllBySearch(String search, Pageable pageable) {
        return jpaHubMovementRepository.findAllBySearch(search, pageable);
    }
}
