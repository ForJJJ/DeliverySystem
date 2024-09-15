package com.forj.company.application.service;

import com.forj.company.application.dto.response.HubListResponseDto;
import com.forj.company.infrastructure.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "hub", configuration = FeignClientConfig.class)
public interface HubClient {

    @GetMapping("/api/v1/hubs")
    HubListResponseDto getHubsInfo(
            @RequestHeader(value = "X-Server-Request", required = false) String serverRequest
    );
}
