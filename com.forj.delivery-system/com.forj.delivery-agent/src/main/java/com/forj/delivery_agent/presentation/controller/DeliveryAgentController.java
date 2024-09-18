package com.forj.delivery_agent.presentation.controller;

import com.forj.delivery_agent.application.dto.request.DeliveryAgentRequestDto;
import com.forj.delivery_agent.application.dto.response.DeliveryAgentGetResponseDto;
import com.forj.delivery_agent.application.service.DeliveryAgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/delivery-agents")
@RequiredArgsConstructor
public class DeliveryAgentController {

    private final DeliveryAgentService deliveryAgentService;

    @PostMapping("/{deliveryAgentId}/signup")
    @PreAuthorize("hasAuthority('DELIVERYAGENT')")
    public ResponseEntity<Void> signupDeliveryAgent(@PathVariable Long deliveryAgentId,
                                                    @RequestBody DeliveryAgentRequestDto requestDto) {
        deliveryAgentService.signupDeliveryAgent(deliveryAgentId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{deliveryAgentId}")
    @PreAuthorize("hasAnyAuthority('DELIVERYAGENT', 'MASTER')")
    public ResponseEntity<DeliveryAgentGetResponseDto> getDeliveryAgent(@PathVariable Long deliveryAgentId) {
        DeliveryAgentGetResponseDto deliveryAgent = deliveryAgentService.getDeliveryAgent(deliveryAgentId);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryAgent);
    }

    @PatchMapping("/{deliveryAgentId}")
    @PreAuthorize("hasAuthority('DELIVERYAGENT')")
    public ResponseEntity<Void> updateDeliveryAgent(@PathVariable Long deliveryAgentId, @RequestBody DeliveryAgentRequestDto requestDto) {
        deliveryAgentService.updateDeliveryAgent(deliveryAgentId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
