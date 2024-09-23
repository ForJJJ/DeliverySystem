package com.forj.delivery_history.application.client;

import com.forj.delivery_history.application.dto.HubMovementInfoResponseDto;
import com.forj.delivery_history.infrastructure.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "localhost:19093", configuration = FeignClientConfig.class)
public interface HubMovementClient {

    @GetMapping("/api/v1/hub-movements/departure/{hubMovementId}")
    HubMovementInfoResponseDto getHubMovementInfo(
            @PathVariable UUID hubMovementId,
            @RequestHeader("X-User-Role") String role
    );

}
