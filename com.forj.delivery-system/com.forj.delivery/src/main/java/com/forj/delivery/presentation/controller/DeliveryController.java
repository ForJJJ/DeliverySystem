package com.forj.delivery.presentation.controller;

import com.forj.delivery.application.dto.request.DriverAssignRequestDto;
import com.forj.delivery.application.dto.response.DeliveryListResponseDto;
import com.forj.delivery.application.dto.response.DeliveryResponseDto;
import com.forj.delivery.application.service.DeliveryService;
import com.forj.delivery.domain.model.Delivery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    // 배송 단건 조회
    @GetMapping("{delivery_id}")
    public DeliveryResponseDto getfindById(
            @PathVariable("delivery_id") UUID delivery_id
    ) {
        return deliveryService.getFindById(delivery_id);
    }

    // 배송 전체 조회
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MASTER')")
    public ResponseEntity<DeliveryListResponseDto> getAllDelivery(
            Pageable pageable
    ) {
        return ResponseEntity.ok(deliveryService.getAllDelivery(pageable));
    }

    // 주문 ID로 배송 조회
    @GetMapping("/orders/{order_id}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBCOMPANY')")
    public ResponseEntity<DeliveryResponseDto> getDeliveryByOrderId(
            @PathVariable("order_id") UUID orderId
    ) {
        return ResponseEntity.ok(deliveryService.getFindByOrderId(orderId));
    }

    // 배송 기록 삭제
    @DeleteMapping("/{delivery_id}")
    @PreAuthorize("hasAnyAuthority('MASTER')")
    public ResponseEntity<Boolean> deleteDelivery(
            @PathVariable("delivery_id") UUID deliveryId
    ) {
        return ResponseEntity.ok(deliveryService.deleteDelivery(deliveryId));
    }

    // 배송 기사님 배달 매핑
    @PostMapping("/assign")
    @PreAuthorize("hasAnyAuthority('MASTER', 'DELIVERYAGENT')")
    public void assignDeliveries(
            @RequestBody DriverAssignRequestDto requestDto
    ) {
        deliveryService.assignDelivery(requestDto);
    }

    @GetMapping("/{deliveryAgentId}/deliveryInfo")
    public ResponseEntity<List<Delivery>> getDeliveriesByDeliveryAgentId(@PathVariable Long deliveryAgentId) {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryService.getDeliveriesByDeliveryAgentId(deliveryAgentId));
    }

    @GetMapping("/{deliveryAgentId}/deliveryCount")
    public ResponseEntity<Long> getDeliveriesByAgentIdAndTimeRange(@PathVariable("deliveryAgentId") Long deliveryAgentId,
                                                                   @RequestParam("hubId") UUID hubId,
                                                                   @RequestParam("startTime") LocalDateTime startTime,
                                                                   @RequestParam("endTime") LocalDateTime endTime) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(deliveryService.getDeliveriesByAgentIdAndTimeRange(deliveryAgentId, hubId, startTime, endTime));
    }

}
