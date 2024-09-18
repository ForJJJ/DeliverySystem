package com.forj.order.presentation.controller;

import com.forj.order.application.dto.response.OrderListResponseDto;
import com.forj.order.application.dto.response.OrderResponseDto;
import com.forj.order.application.service.OrderService;
import com.forj.order.application.dto.request.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBCOMPANY')")
    public ResponseEntity<OrderResponseDto> getOrder(
        @RequestBody OrderRequestDto orderRequestDto
    ){

        return ResponseEntity.ok(orderService.createOrder(orderRequestDto));
    }

    // 주문 단건 조회
    @GetMapping("/{order_id}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBCOMPANY')")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable("order_id") UUID orderId
    ){
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // 주문 전체 조회
    @GetMapping
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBCOMPANY')")
    public ResponseEntity<OrderListResponseDto> getAllOrder(
            Pageable pageable
    ){
        return ResponseEntity.ok(orderService.getAllOrder(pageable));
    }

    // 주문 내용 수정
    @PatchMapping("/{order_id}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBCOMPANY')")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @RequestBody OrderRequestDto orderRequestDto,
            @PathVariable("order_id") UUID orderId
    ){
        return ResponseEntity.ok(orderService.updateOrder(orderId,orderRequestDto));
    }

    // 주문 내역 삭제하기
    @DeleteMapping("/{order_id}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBCOMPANY')")
    public ResponseEntity<Boolean> deleteOrder(
            @PathVariable("order_id") UUID orderId
    ){
       return ResponseEntity.ok(orderService.deleteOrder(orderId));
    }

    // 주문 취소 요청
    @PostMapping("/{order_id}/cancel")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBCOMPANY')")
    public void cancelOrder(
            @PathVariable("order_id") UUID orderId
    ){
        orderService.cancelOrder(orderId);
    }
}
