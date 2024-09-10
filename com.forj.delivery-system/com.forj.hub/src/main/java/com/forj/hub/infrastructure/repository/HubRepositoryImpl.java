package com.forj.hub.infrastructure.repository;

import com.forj.hub.domain.model.Hub;
import com.forj.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HubRepositoryImpl implements HubRepository {

    private final JPAHubRepository jpaHubRepository;

    @Override
    public Hub findById(UUID hubId) {
        return jpaHubRepository.findById(hubId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Hub save(Hub hub) {
        return jpaHubRepository.save(hub);
    }

    @Override
    public Hub findByIdAndIsDeletedFalse(UUID hubId) {
        return jpaHubRepository.findByIdAndIsDeletedFalse(hubId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Hub> findAll() {
        return jpaHubRepository.findAll();
    }
}
