package com.forj.ai.application.service;

import com.forj.ai.infrastructure.config.FeignConfig;
import com.forj.ai.infrastructure.dto.HubDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "hub", configuration = FeignConfig.class)
public interface HubServiceClient {

    @GetMapping("/api/v1/hubs/{hubId}")
    HubDto getLonLatByHubId(@PathVariable UUID hubId,
                            @RequestHeader(value = "X-Server-Request", required = false) String serverRequest);

}
