package com.forj.order.presentation.controller;

import com.forj.order.application.dto.OrderResponseDto;
import com.forj.order.application.service.OrderService;
import com.forj.order.presentation.request.OrderRequestDto;
import com.forj.order.presentation.request.OrderStatusRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping
    public OrderResponseDto getOrder(
        @RequestBody OrderRequestDto orderRequestDto,
        @RequestHeader(value = "X-User-Id") String userId,
        @RequestHeader(value = "X-Role") String role
    ){

        return orderService.createOrder(orderRequestDto,userId);
    }

    // 주문 단건 조회
    @GetMapping("/{order_id}")
    public OrderResponseDto getOrderById(
            @PathVariable("order_id") UUID orderId,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role
    ){
        return orderService.getOrderById(orderId);
    }

    // 주문 전체 조회
    // 주문 내용 수정
    @PatchMapping("/{order_id}")
    public OrderResponseDto updateOrder(
            @RequestBody OrderRequestDto orderRequestDto,
            @PathVariable("order_id") UUID orderId,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role
    ){
        return orderService.updateOrder(orderId,orderRequestDto);
    }

    // 주문 내역 삭제하기
    @DeleteMapping("/{order_id}")
    public void deleteOrder(
            @PathVariable("order_id") UUID orderId,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role
    ){
        orderService.deleteOrder(orderId);
    }

    // 주문 상태 변경하기
    @PutMapping("/{order_id}/status")
    public void updateOrderStatus(
            @PathVariable("order_id") UUID orderId,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role,
            @RequestBody OrderStatusRequestDto requestDto
    ){
        orderService.updateOrderStatus(orderId,requestDto);
    }

    @PostMapping("/{order_id}/cancel")
    public void cancelOrder(
            @PathVariable("order_id") UUID orderId,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role
    ){
        orderService.cancelOrder(orderId);
    }
}
