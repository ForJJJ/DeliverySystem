package com.forj.delivery.presentation.controller;

import com.forj.delivery.application.dto.request.DriverAssignRequestDto;
import com.forj.delivery.application.dto.response.DeliveryResponseDto;
import com.forj.delivery.application.service.DeliveryAssignService;
import com.forj.delivery.application.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final DeliveryAssignService deliveryAssignService;

    // 배송 단건 조회
    @GetMapping("{delivery_id}")
    public DeliveryResponseDto getfindById(
            @PathVariable("delivery_id") UUID delivery_id,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role
    ) {
        return deliveryService.getfindById(delivery_id);
    }

    // 배송 기사님 배달 매핑
    @PostMapping("/assign")
    public void assignDeliveries(
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role,
            @RequestBody DriverAssignRequestDto requestDto
    ){
        deliveryAssignService.assignDriver(userId,requestDto);
    }
}
