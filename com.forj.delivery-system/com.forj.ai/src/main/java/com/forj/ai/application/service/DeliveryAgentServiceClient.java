package com.forj.ai.application.service;

import com.forj.ai.infrastructure.config.FeignConfig;
import com.forj.ai.infrastructure.dto.DeliveryAgentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "delivery-agent", configuration = FeignConfig.class)
public interface DeliveryAgentServiceClient {

    @GetMapping("/api/v1/delivery-agents/info")
    List<DeliveryAgentDto> getAllDeliveryAgentsByCompanyRole();

    @GetMapping("/api/v1/delivery-agents/{deliveryAgentId}")
    DeliveryAgentDto getHubIdByDeliveryAgentId(@PathVariable Long deliveryAgentId);

}
