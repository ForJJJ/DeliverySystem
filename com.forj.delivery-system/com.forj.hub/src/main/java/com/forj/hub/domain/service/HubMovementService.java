package com.forj.hub.domain.service;

import com.forj.hub.domain.model.Hub;
import com.forj.hub.domain.model.HubMovement;
import com.forj.hub.domain.repository.HubMovementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubMovementService {

    private final HubMovementRepository hubMovementRepository;

    @Transactional
    public HubMovement createHubMovement(Hub departureHub, Hub arrivalHub, Duration duration) {

        String routeName = getRouteName(departureHub, arrivalHub);

        HubMovement hubMovement = HubMovement.createHubMovement(
                departureHub.getId(), arrivalHub.getId(), duration, routeName
        );

        return hubMovementRepository.save(hubMovement);
    }

    @Transactional(readOnly = true)
    public HubMovement getHubMovementInfo(UUID hubMovementId) {

        return hubMovementRepository.findById(hubMovementId);
    }

    @Transactional(readOnly = true)
    public HubMovement getHubMovementInfoByDepartureHub(UUID departureHubId) {

        return hubMovementRepository.findByDepartureHubId(departureHubId);
    }

    @Transactional(readOnly = true)
    public Page<HubMovement> getHubMovementListInfo(String search, Pageable pageable) {

        return hubMovementRepository.findAllBySearch(search, pageable);
    }

    @Transactional
    public HubMovement updateHubMovementInfo(UUID hubMovementId, String route) {

        HubMovement hubMovement = hubMovementRepository
                .findById(hubMovementId);

        hubMovement.updateHubMovementInfo(route);

        return hubMovement;
    }

    @Transactional
    public HubMovement updateHubMovementDuration(
            HubMovement hubMovement, Duration duration
    ) {

        hubMovement.updateDuration(duration);

        return hubMovement;
    }

    @Transactional
    public void deleteHubMovement(UUID hubMovementId) {

        HubMovement hubMovement = hubMovementRepository
                .findById(hubMovementId);
        hubMovement.delete(getCurrentUserId());
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private static String getRouteName(Hub departureHub, Hub arrivalHub) {
        return new StringJoiner("-")
                .add(Objects.requireNonNull(
                        departureHub.getName(),
                        "departure hub name is null")
                )
                .add(Objects.requireNonNull(
                        arrivalHub.getName(),
                        "arrival hub name is null")
                )
                .toString();
    }
}
