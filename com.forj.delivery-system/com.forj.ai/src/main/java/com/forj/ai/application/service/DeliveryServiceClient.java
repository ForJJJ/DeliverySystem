package com.forj.ai.application.service;

import com.forj.ai.infrastructure.config.FeignConfig;
import com.forj.ai.infrastructure.dto.DeliveryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "delivery", configuration = FeignConfig.class)
public interface DeliveryServiceClient {

    @GetMapping("/api/v1/deliveries/{deliveryAgentId}/deliveryInfo")
    List<DeliveryDto> getDeliveriesByDeliveryAgentId(@PathVariable Long deliveryAgentId);

    @GetMapping("/api/v1/deliveries/{deliveryAgentId}/deliveryCount")
    Long getDeliveriesByAgentIdAndTimeRange(@PathVariable("deliveryAgentId") Long deliveryAgentId,
                                            @RequestParam("hubId") UUID hubId,
                                            @RequestParam("startTime") LocalDateTime startTime,
                                            @RequestParam("endTime") LocalDateTime endTime);

}
