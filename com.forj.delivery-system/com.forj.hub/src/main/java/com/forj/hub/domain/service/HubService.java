package com.forj.hub.domain.service;

import com.forj.common.security.SecurityUtil;
import com.forj.hub.domain.model.Hub;
import com.forj.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubService {

    private final HubRepository hubRepository;

    @Transactional
    public Hub createHub(String name, String roadAddress, double x, double y) {

        Hub hub = Hub.toHubEntity(name, roadAddress, x, y);

        return hubRepository.save(hub);
    }

    @Transactional(readOnly = true)
    public Hub getHubInfo(String hubId) {

        return hubRepository.findByIdAndIsDeletedFalse(UUID.fromString(hubId));
    }

    @Transactional(readOnly = true)
    public List<Hub> getHubsInfo() {

        return hubRepository.findAll();
    }

    @Transactional
    public Hub updateHubInfo(String hubId, String name, String address, Double x, Double y) {

        Hub hub = hubRepository.findById(UUID.fromString(hubId));

        hub.updateHub(name, address, x, y);

        return hub;
    }

    @Transactional
    public Boolean deleteHub(String hubId) {

        Hub hub = hubRepository.findById(UUID.fromString(hubId));

        hub.delete(SecurityUtil.getCurrentUserId());

        return true;
    }
}
