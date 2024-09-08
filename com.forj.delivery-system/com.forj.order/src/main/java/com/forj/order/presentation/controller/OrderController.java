package com.forj.order.presentation.controller;

import com.forj.order.application.dto.OrderResponseDto;
import com.forj.order.application.service.OrderService;
import com.forj.order.presentation.request.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
