package com.forj.product.application.service;

import com.forj.product.application.dto.response.HubInfoResponseDto;
import com.forj.product.infrastructure.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "hub", configuration = FeignClientConfig.class)
public interface HubClient {

    @GetMapping("/api/v1/hubs/{hubId}")
    HubInfoResponseDto getHubInfo(
            @PathVariable UUID hubId,
            @RequestHeader(value = "X-Server-Request", required = false) String serverRequest
    );
}
