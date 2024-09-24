package com.forj.ai.presentation.controller;

import com.forj.ai.application.dto.response.AiHistoryResponseDto;
import com.forj.ai.application.dto.response.SlackMessageResponseDto;
import com.forj.ai.application.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ais")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @GetMapping("/weather-delivery")
    @PreAuthorize("hasAnyAuthority('MASTER')")
    public ResponseEntity<List<SlackMessageResponseDto>> requestForCompanyDeliveryAgent() {
        return ResponseEntity.status(HttpStatus.OK).body(aiService.getInfoForCompanyDeliveryAgents());
    }

    @PreAuthorize("hasAnyAuthority('MASTER')")
    @GetMapping("/order/{deliveryAgentId}")
    public ResponseEntity<String> requestForHubDeliveryAgent(@PathVariable Long deliveryAgentId) {
        return ResponseEntity.status(HttpStatus.OK).body(aiService.requestForHubDeliveryAgent(deliveryAgentId));
    }

    @PreAuthorize("hasAnyAuthority('MASTER')")
    @GetMapping("/{aiId}")
    public ResponseEntity<AiHistoryResponseDto> getAiHistory(@PathVariable UUID aiId) {
        AiHistoryResponseDto response = aiService.getAiHistory(aiId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
