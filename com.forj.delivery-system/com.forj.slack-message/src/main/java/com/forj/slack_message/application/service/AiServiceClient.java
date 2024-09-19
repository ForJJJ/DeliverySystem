package com.forj.slack_message.application.service;

import com.forj.slack_message.application.dto.request.SlackMessageResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "ai")
public interface AiServiceClient {

    @GetMapping("/api/v1/ais/weather-delivery")
    List<SlackMessageResponseDto> getInfoForCompanyDeliveryAgent(@RequestHeader("X-User-Id") String userId,
                                                                 @RequestHeader("X-User-Role") String role);

    @GetMapping("/api/v1/ais/order/{deliveryAgentId}")
    String getInfoForHubDeliveryAgent(@PathVariable Long deliveryAgentId);

}
