package com.forj.delivery.presentation.controller;

import com.forj.delivery.application.dto.response.DeliveryResponseDto;
import com.forj.delivery.application.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    // 배송 단건 조회
    @GetMapping("{delivery_id}")
    public DeliveryResponseDto getfindById(
            @PathVariable("delivery_id") UUID delivery_id,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role
    ) {
        return deliveryService.getfindById(delivery_id);
    }
}
