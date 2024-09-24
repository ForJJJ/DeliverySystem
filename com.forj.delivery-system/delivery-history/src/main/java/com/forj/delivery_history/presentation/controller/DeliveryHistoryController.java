package com.forj.delivery_history.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery-histories")
public class DeliveryHistoryController {

    private final DeliveryHistoryService deliveryHistoryService;

    // 배송 도착 처리
    @PutMapping("/arrive")
    public void arrivedHub(){
        deliveryHistoryService.arrivedHub();
    }
}
