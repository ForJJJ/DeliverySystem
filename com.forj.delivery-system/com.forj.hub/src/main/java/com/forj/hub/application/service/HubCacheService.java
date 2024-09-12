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

@Slf4j
@Service
@RequiredArgsConstructor
public class HubCacheService {

    private final HubService hubService;

    @Cacheable(value = "hubCache", key = "#hubId")
    public HubInfoResponseDto getHubInfo(String hubId, boolean isPrivate) {

        log.info("Hub info from database for hubId : {}, isPrivate : {}", hubId, isPrivate);

        Hub hub = hubService.getHubInfo(hubId);

        return convertHubToDto(hub, isPrivate);
    }

    @Cacheable(value = "hubCache", key = "'allHubs'")
    public HubListResponseDto getHubsInfo(boolean isPrivate) {

        log.info("Hubs info from database for isPrivate : {}", isPrivate);

        List<Hub> hubs = hubService.getHubsInfo();

        List<HubInfoResponseDto> list = hubs.stream()
                .map(hub -> this.convertHubToDto(hub, isPrivate)).toList();

        return new HubListResponseDto(list);
    }

    private HubInfoResponseDto convertHubToDto(Hub hub, boolean isPrivate) {

        if (isPrivate) {
            return HubInfoResponseDto.forPrivateResponse(
                    hub.getId().toString(),
                    hub.getName(),
                    hub.getAddress(),
                    hub.getLongitude(),
                    hub.getLatitude()
            );
        }

        return HubInfoResponseDto.forPublicResponse(
                hub.getName(),
                hub.getAddress()
        );
    }
}
