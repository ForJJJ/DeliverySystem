package com.forj.hub.application.service;

import com.forj.hub.application.dto.request.HubMovementRequestDto;
import com.forj.hub.application.dto.request.HubMovementUpdateRequestDto;
import com.forj.hub.application.dto.response.*;
import com.forj.hub.domain.model.Hub;
import com.forj.hub.domain.model.HubMovement;
import com.forj.hub.domain.service.HubMovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubMovementApplicationService {

    private final NaverGeoClient naverGeoClient;
    private final HubMovementService hubMovementService;
    private final HubCacheService hubCacheService;

    public HubMovementInfoResponseDto createHubMovement(HubMovementRequestDto request) {

        HubInfoResponseDto departureHub = hubCacheService
                .getHubInfo(request.departureHubId());
        HubInfoResponseDto arrivalHub = hubCacheService
                .getHubInfo(request.arrivalHubId());

        Long duration = calculateDurationBetweenHubs(arrivalHub, departureHub);

        HubMovement hubMovement = hubMovementService.createHubMovement(
                convertDtoToHub(departureHub),
                convertDtoToHub(arrivalHub),
                Duration.ofMillis(duration)
        );

        return convertHubMovementToDto(hubMovement);
    }

    @Async("createHubMovement")
    @Transactional
    public void createHubMovementAsync(UUID departureHubId) {

        HubInfoResponseDto departureHub = hubCacheService.getHubInfo(departureHubId);
        HubListResponseDto hubsInfo = hubCacheService.getHubsInfo();

        for (HubInfoResponseDto arrivalHub : hubsInfo.hubList()) {

            if (departureHub.id().equals(arrivalHub.id()) ||
                departureHub.address().equals(arrivalHub.address())) {
                continue;
            }

            Long duration = calculateDurationBetweenHubs(arrivalHub, departureHub);

            HubMovement hubMovement = hubMovementService.createHubMovement(
                    convertDtoToHub(departureHub),
                    convertDtoToHub(arrivalHub),
                    Duration.ofMillis(duration)
            );
        }
    }

    @Cacheable(value = "hubMovementCache", key = "#hubMovementId")
    public HubMovementInfoResponseDto getHubMovementInfo(UUID hubMovementId) {

        HubMovement hubMovement = hubMovementService.getHubMovementInfo(hubMovementId);

        return convertHubMovementToDto(hubMovement);
    }

    @Cacheable(value = "hubMovementCacheByDeparture", key = "#departureHubId")
    public HubMovementInfoResponseDto getHubMovementInfoByDepartureHub(
            UUID departureHubId
    ) {

        HubMovement hubMovement =
                hubMovementService.getHubMovementInfoByDepartureHub(departureHubId);

        return convertHubMovementToDto(hubMovement);
    }

    @Cacheable(value = "hubMovementCache", key = "#search")
    public HubMovementListResponseDto getHubMovementListInfo(
            String search, Pageable pageable
    ) {

        Page<HubMovement> hubMovements = hubMovementService
                .getHubMovementListInfo(search, pageable);

        return new HubMovementListResponseDto(
                hubMovements.map(this::convertHubMovementToDto));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "hubMovementCache", key = "#hubMovementId"),
            @CacheEvict(value = "hubMovementCacheByDeparture", key = "#result.departureHubId()")
    })
    public HubMovementInfoResponseDto updateHubMovementInfo(
            UUID hubMovementId, HubMovementUpdateRequestDto request
    ) {
        
        HubMovement hubMovement = hubMovementService
                .updateHubMovementInfo(hubMovementId, request.route());
        
        return convertHubMovementToDto(hubMovement);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "hubMovementCache", key = "#hubMovementId"),
            @CacheEvict(value = "hubMovementCacheByDeparture", key = "#result.departureHubId()")
    })
    public HubMovementInfoResponseDto updateHubMovementDuration(UUID hubMovementId) {

        HubMovement hubMovement = hubMovementService.getHubMovementInfo(hubMovementId);
        HubInfoResponseDto departureHub = hubCacheService.getHubInfo(hubMovement.getDepartureHubId());
        HubInfoResponseDto arrivalHub = hubCacheService.getHubInfo(hubMovement.getArrivalHubId());

        String start = getStringPoint(
                departureHub.longitude(),
                departureHub.latitude()
        );

        String goal = getStringPoint(
                arrivalHub.longitude(),
                arrivalHub.latitude()
        );

        NaverDrivingResponseDto routeInfo = naverGeoClient.getRouteInfo(start, goal);

        HubMovement updatedHubMovement = hubMovementService.updateHubMovementDuration(
                hubMovement,
                Duration.ofSeconds(routeInfo.route().traoptimal().get(0).summary().duration())
        );

        return convertHubMovementToDto(updatedHubMovement);
    }

    @CacheEvict(cacheNames = "hubMovementCache", key = "#hubMovementId")
    public Boolean deleteHubMovement(UUID hubMovementId) {

        hubMovementService.deleteHubMovement(hubMovementId);

        return true;
    }

    public void addNewHubMovement(UUID hubId) {

        HubInfoResponseDto newHubInfo = hubCacheService.getHubInfo(hubId);
        HubListResponseDto  existingHubsInfo = hubCacheService.getHubsInfo();

        HubInfoResponseDto[] bestInsertPosition =
                findBestInsertPosition(newHubInfo, existingHubsInfo.hubList());
        HubInfoResponseDto previousHub = bestInsertPosition[0];
        HubInfoResponseDto nextHub = bestInsertPosition[1];

        // 기존 허브 간 연결 정보 제거 (previousHub -> nextHub)
        HubMovement hubMovementInfoByDepartureHub =
                hubMovementService.getHubMovementInfoByDepartureHub(previousHub.id());
        hubMovementService.deleteHubMovement(hubMovementInfoByDepartureHub.getId());

        Long durationToNewHub = calculateDurationBetweenHubs(newHubInfo, previousHub);
        Long durationFromNewHub = calculateDurationBetweenHubs(nextHub, newHubInfo);

        // 새 허브 삽입 후 경로 생성 (previousHub -> newHub -> nextHub)
        hubMovementService.createHubMovement(
                convertDtoToHub(previousHub),
                convertDtoToHub(newHubInfo),
                Duration.ofMillis(durationToNewHub)
        );
        hubMovementService.createHubMovement(
                convertDtoToHub(newHubInfo),
                convertDtoToHub(nextHub),
                Duration.ofMillis(durationFromNewHub)
        );
    }

    private HubInfoResponseDto[] findBestInsertPosition
            (HubInfoResponseDto newHub, List<HubInfoResponseDto> existingHubs
            ) {
        double minExtraDistance = Double.MAX_VALUE;
        HubInfoResponseDto[] hubArr = new HubInfoResponseDto[2];

        for (int i = 0; i < existingHubs.size(); i++) {

            if (newHub.id().equals(existingHubs.get(i).id())) {
                continue;
            }

            HubMovement hubMovementInfoByDepartureHub = hubMovementService
                    .getHubMovementInfoByDepartureHub(existingHubs.get(i).id());

            HubInfoResponseDto departureHub = hubCacheService
                    .getHubInfo(hubMovementInfoByDepartureHub.getDepartureHubId());
            HubInfoResponseDto arrivalHub = hubCacheService
                    .getHubInfo(hubMovementInfoByDepartureHub.getArrivalHubId());

            log.info("departureHub : {}, arrivalHub : {}",
                    departureHub.name(), arrivalHub.name()
            );

            long currentDistance = hubMovementInfoByDepartureHub.getDuration().toMillis();

            Long durationToNewHub = calculateDurationBetweenHubs(newHub, departureHub);
            Long durationFromNewHub = calculateDurationBetweenHubs(arrivalHub, newHub);

            long newDistance = durationToNewHub + durationFromNewHub;

            // 거리가 최소인 위치를 선택
            if (newDistance - currentDistance < minExtraDistance) {
                minExtraDistance = newDistance - currentDistance;
                hubArr[0] = departureHub;
                hubArr[1] = arrivalHub;
            }
        }
        return hubArr;
    }

    private Long calculateDurationBetweenHubs(
            HubInfoResponseDto arrivalHub, HubInfoResponseDto departureHub
    ) {
        String start = getStringPoint(departureHub.longitude(), departureHub.latitude());
        String goal = getStringPoint(arrivalHub.longitude(), arrivalHub.latitude());
        return getDurationFromNaver(start, goal);
    }

    private String getStringPoint(double longitude, double latitude) {
        return Objects.toString(longitude, "0.0")
                + "," + Objects.toString(latitude, "0.0");
    }

    private Long getDurationFromNaver(String start, String goal) {
        try {
            NaverDrivingResponseDto routeInfo = naverGeoClient.getRouteInfo(start, goal);
            log.info("NaverDrivingResponse : {}, {}",routeInfo.code(), routeInfo.message());

            Integer distance = routeInfo.route().traoptimal().get(0).summary().distance();
            Long duration = routeInfo.route().traoptimal().get(0).summary().duration();
            log.info("Parsing Distance & Duration : {}, {}", distance, duration);
            return duration;

        } catch (Exception e) {
            log.warn("NaverClientError : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private Hub convertDtoToHub(HubInfoResponseDto dto) {
        return Hub.toHubEntityWithId(
                dto.id(),
                dto.name(),
                dto.address(),
                dto.longitude(),
                dto.latitude()
        );
    }

    private HubMovementInfoResponseDto convertHubMovementToDto(
            HubMovement hubMovement
    ) {
        return new HubMovementInfoResponseDto(
                hubMovement.getId(),
                hubMovement.getDepartureHubId(),
                hubMovement.getArrivalHubId(),
                hubMovement.getDuration(),
                hubMovement.getRoute()
        );
    }
}
