package com.forj.hub.application.service;

import com.forj.hub.application.dto.response.HubInfoResponseDto;
import com.forj.hub.application.dto.response.HubListResponseDto;
import com.forj.hub.domain.model.Hub;
import com.forj.hub.domain.service.HubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubCacheService {

    private final HubService hubService;

    @Cacheable(value = "hubCache", key = "#hubId")
    public HubInfoResponseDto getHubInfo(UUID hubId) {

        log.info("Hub info from database for hubId : {}", hubId);

        Hub hub = hubService.getHubInfo(hubId);

        return convertHubToDto(hub);
    }

    @Cacheable(value = "hubCache", key = "'allHubs'")
    public HubListResponseDto getHubsInfo() {

        log.info("Hubs info from database");

        List<Hub> hubs = hubService.getHubsInfo();

        List<HubInfoResponseDto> list = hubs.stream()
                .map(this::convertHubToDto).toList();

        return new HubListResponseDto(list);
    }

    private HubInfoResponseDto convertHubToDto(Hub hub) {
        return HubInfoResponseDto.forPrivateResponse(
                hub.getId(),
                hub.getName(),
                hub.getAddress(),
                hub.getLongitude(),
                hub.getLatitude()
        );
    }
}
