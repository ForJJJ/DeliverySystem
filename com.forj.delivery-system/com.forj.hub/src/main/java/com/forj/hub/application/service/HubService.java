package com.forj.hub.application.service;

import com.forj.hub.application.dto.*;
import com.forj.hub.domain.model.Hub;
import com.forj.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubService {

    private final NaverGeoClient naverGeoClient;
    private final HubRepository hubRepository;

    @Transactional
    @CachePut(value = "hubCache", key = "#result.id")
    public HubInfoResponseDto createHub(HubRequestDto request) {

        // 좌표 가져오기 Naver Geo API
        NaverGeoPointResponseDto geoPointResponseDto = getAddressWithGeoPoint(request);
        AddressDto addressDto = geoPointResponseDto.addresses().get(0);

        // 허브 생성
        Hub hub = Hub.toHubEntity(
                request.name(),
                addressDto.roadAddress(),
                Double.parseDouble(addressDto.x()),
                Double.parseDouble(addressDto.y())
        );

        Hub savedHub = hubRepository.save(hub);

        return new HubInfoResponseDto(
                savedHub.getId().toString(),
                savedHub.getName(),
                savedHub.getAddress(),
                savedHub.getLongitude(),
                savedHub.getLatitude()
        );
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "hubCache", key = "#isPrivate ? #hubId : 'public_' + #hubId")
    public HubInfoResponseDto getHubInfo(String hubId, boolean isPrivate) {

        log.info("Hub info from database for hubId : {}, isPrivate : {}", hubId, isPrivate);

        // is_deleted false
        Hub hub = hubRepository.findByIdAndIsDeletedFalse(UUID.fromString(hubId));

        return getHubInfoResponseDto(isPrivate, hub);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "hubCache", key = "#isPrivate ? 'allHubs' : 'public_allHubs'")
    public HubListResponseDto getHubsInfo(boolean isPrivate) {

        log.info("Hubs info from database, isPrivate : {}", isPrivate);

        List<Hub> hubs = hubRepository.findAll();

        List<HubInfoResponseDto> list =
                hubs.stream().map(hub ->
                        getHubInfoResponseDto(isPrivate, hub)
                ).toList();


        return new HubListResponseDto(list);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "hubCache", key = "#hubId"),
            @CacheEvict(cacheNames = "hubCache", key = "'allHubs'")
    }, put = {
            @CachePut(cacheNames = "hubCache", key = "#result.id")
    })
    public HubInfoResponseDto updateHubInfo(String hubId, HubRequestDto request) {

        Hub hub = hubRepository.findById(UUID.fromString(hubId));

        NaverGeoPointResponseDto geoPointResponseDto = getAddressWithGeoPoint(request);
        AddressDto addressDto = geoPointResponseDto.addresses().get(0);

        hub.updateHub(
                request.name(),
                request.address(),
                Double.parseDouble(addressDto.x()),
                Double.parseDouble(addressDto.y())
        );

        return new HubInfoResponseDto(
                hub.getId().toString(),
                hub.getName(),
                hub.getAddress(),
                hub.getLongitude(),
                hub.getLatitude()
        );
    }

    @Transactional
    @CacheEvict(cacheNames = "hubCache", key = "#hubId")
    public Boolean deleteHub(String hubId) {

        Hub hub = hubRepository.findById(UUID.fromString(hubId));

        hub.delete(getCurrentUserId());

        return true;
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private NaverGeoPointResponseDto getAddressWithGeoPoint(HubRequestDto request) {

        try {
            NaverGeoPointResponseDto geoPointResponseDto =
                    naverGeoClient.getGeoPoint(request.address());
            log.info("status : {}, errorMessage : {}",
                    geoPointResponseDto.status(),
                    geoPointResponseDto.errorMessage()
            );
            return geoPointResponseDto;
        } catch (Exception e) {
            log.warn("NaverClientError : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private HubInfoResponseDto getHubInfoResponseDto(boolean isPrivate, Hub hub) {
        if (isPrivate) {
            return HubInfoResponseDto.forPrivateResponse(
                    hub.getId().toString(),
                    hub.getName(),
                    hub.getAddress(),
                    hub.getLongitude(),
                    hub.getLatitude()
            );
        }

        return HubInfoResponseDto.forPublicResponse(hub.getName(), hub.getAddress());
    }
}
