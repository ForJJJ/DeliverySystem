package com.forj.hub.application.service;

import com.forj.hub.application.dto.request.HubMovementRequestDto;
import com.forj.hub.application.dto.request.HubMovementUpdateRequestDto;
import com.forj.hub.application.dto.response.HubInfoResponseDto;
import com.forj.hub.application.dto.response.HubMovementInfoResponseDto;
import com.forj.hub.application.dto.response.HubMovementListResponseDto;
import com.forj.hub.application.dto.response.NaverDrivingResponseDto;
import com.forj.hub.domain.model.Hub;
import com.forj.hub.domain.model.HubMovement;
import com.forj.hub.domain.service.HubMovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .getHubInfo(request.departureHubId(), true);
        HubInfoResponseDto arrivalHub = hubCacheService
                .getHubInfo(request.arrivalHubId(), true);

        String start = getStringPoint(departureHub.x(), departureHub.y());
        String goal = getStringPoint(arrivalHub.x(), arrivalHub.y());

        Long duration = getDurationFromNaver(start, goal);

        HubMovement hubMovement = hubMovementService.createHubMovement(
                convertDtoToHub(departureHub),
                convertDtoToHub(arrivalHub),
                duration
        );

        return convertHubMovementToDto(hubMovement);
    }

    @Cacheable(value = "hubMovementCache", key = "#hubMovementId")
    public HubMovementInfoResponseDto getHubMovementInfo(String hubMovementId) {

        HubMovement hubMovement = hubMovementService.getHubMovementInfo(hubMovementId);

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

    @CacheEvict(cacheNames = "hubMovementCache", key = "#hubMovementId")
    public HubMovementInfoResponseDto updateHubMovementInfo(
            String hubMovementId, HubMovementUpdateRequestDto request
    ) {
        
        HubMovement hubMovement = hubMovementService
                .updateHubMovementInfo(hubMovementId, request.route());
        
        return convertHubMovementToDto(hubMovement);
    }

    @CacheEvict(cacheNames = "hubMovementCache", key = "#hubMovementId")
    public HubMovementInfoResponseDto updateHubMovementDuration(String hubMovementId) {

        HubMovement hubMovement = hubMovementService.getHubMovementInfo(hubMovementId);
        
        String start = getStringPoint(
                hubMovement.getDepartureHub().getLongitude(),
                hubMovement.getDepartureHub().getLatitude()
        );

        String goal = getStringPoint(
                hubMovement.getArrivalHub().getLongitude(),
                hubMovement.getArrivalHub().getLatitude()
        );

        NaverDrivingResponseDto routeInfo = naverGeoClient.getRouteInfo(start, goal);

        HubMovement updatedHubMovement = hubMovementService.updateHubMovementDuration(
                hubMovement,
                routeInfo.route().traoptimal().get(0).summary().duration()
        );

        return convertHubMovementToDto(updatedHubMovement);
    }

    @CacheEvict(cacheNames = "hubMovementCache", key = "#hubMovementId")
    public Boolean deleteHubMovement(String hubMovementId) {

        hubMovementService.deleteHubMovement(hubMovementId);

        return true;
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
                UUID.fromString(dto.id()),
                dto.name(),
                dto.address(),
                dto.x(),
                dto.y()
        );
    }

    private HubMovementInfoResponseDto convertHubMovementToDto(
            HubMovement hubMovement
    ) {
        return new HubMovementInfoResponseDto(
                hubMovement.getId().toString(),
                hubMovement.getDepartureHub().getId().toString(),
                hubMovement.getArrivalHub().getId().toString(),
                hubMovement.getDuration().toString(),
                hubMovement.getRoute()
        );
    }
}
