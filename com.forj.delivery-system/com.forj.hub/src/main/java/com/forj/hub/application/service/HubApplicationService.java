package com.forj.hub.application.service;

import com.forj.hub.application.dto.request.HubRequestDto;
import com.forj.hub.application.dto.response.AddressDto;
import com.forj.hub.application.dto.response.HubInfoResponseDto;
import com.forj.hub.application.dto.response.HubListResponseDto;
import com.forj.hub.application.dto.response.NaverGeoPointResponseDto;
import com.forj.hub.domain.model.Hub;
import com.forj.hub.domain.service.HubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubApplicationService {

    private final HubService hubService;
    private final HubCacheService hubCacheService;
    private final NaverGeoClient naverGeoClient;

    @CachePut(value = "hubCache", key = "#result.id")
    public HubInfoResponseDto createHub(HubRequestDto request) {

        NaverGeoPointResponseDto geoPointResponseDto = getAddressWithGeoPoint(request);
        AddressDto addressDto = geoPointResponseDto.addresses().get(0);

        Hub hub = hubService.createHub(
                request.name(),
                addressDto.roadAddress(),
                Double.parseDouble(addressDto.x()),
                Double.parseDouble(addressDto.y())
        );

        return convertHubToDto(hub);
    }

    public HubInfoResponseDto getHubInfo(String hubId, boolean isPrivate) {

        HubInfoResponseDto hubInfo = hubCacheService.getHubInfo(hubId);

        if (isPrivate) {
            return hubInfo;
        }

        return forPublicResponse(hubInfo);
    }

    public HubListResponseDto getHubsInfo(boolean isPrivate) {

        HubListResponseDto hubsInfo = hubCacheService.getHubsInfo();

        if (isPrivate) {
            return hubsInfo;
        }

        return new HubListResponseDto(
                hubsInfo.hubList().stream().map(this::forPublicResponse).toList());
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "hubCache", key = "#hubId"),
            @CacheEvict(cacheNames = "hubCache", key = "'allHubs'")
    }, put = {
            @CachePut(cacheNames = "hubCache", key = "#result.id")
    })
    public HubInfoResponseDto updateHubInfo(String hubId, HubRequestDto request) {

        NaverGeoPointResponseDto geoPointResponseDto = getAddressWithGeoPoint(request);
        AddressDto addressDto = geoPointResponseDto.addresses().get(0);

        Hub hub = hubService.updateHubInfo(
                hubId,
                request.name(),
                request.address(),
                Double.parseDouble(addressDto.x()),
                Double.parseDouble(addressDto.y())
        );

        return convertHubToDto(hub);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "hubCache", key = "#hubId"),
            @CacheEvict(cacheNames = "hubCache", key = "'allHubs'")
    })
    public Boolean deleteHub(String hubId) {

        hubService.deleteHub(hubId);

        return true;
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

    private HubInfoResponseDto convertHubToDto(Hub hub) {
        return HubInfoResponseDto.forPrivateResponse(
                hub.getId().toString(),
                hub.getName(),
                hub.getAddress(),
                hub.getLongitude(),
                hub.getLatitude()
        );
    }

    private HubInfoResponseDto forPublicResponse(HubInfoResponseDto hubInfo) {
        return HubInfoResponseDto.forPublicResponse(
                hubInfo.name(),
                hubInfo.address()
        );
    }
}
