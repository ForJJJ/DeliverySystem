package com.forj.order.application.service;

import com.forj.order.application.dto.OrderResponseDto;
import com.forj.order.domain.model.Order;
import com.forj.order.domain.repostiory.OrderRepository;
import com.forj.order.presentation.request.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

// 기본적인 CRUD
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // 주문 생성
    @Transactional
    public OrderResponseDto createOrder(
            OrderRequestDto orderRequestDto,
            String userId
    ) {
        Order order = Order.create(
                UUID.fromString(userId),
                orderRequestDto.getReceivingCompanyId(),
                orderRequestDto.getProductId(),
                orderRequestDto.getQuantity(),
                orderRequestDto.getDeliveryId()
        );

        Order savedOrder = orderRepository.save(order);

        return OrderResponseDto.fromOrder(savedOrder);
    }

    // 주문 단건 조회
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(
            UUID orderId
    ) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 주문은 찾을 수가 없습니다."));
        return OrderResponseDto.fromOrder(order);
    }

    // 주문 전체 조회

    // 주문 내용 수정
    @Transactional
    public OrderResponseDto updateOrder(
            UUID orderId,
            OrderRequestDto orderRequestDto
    ) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 주문은 찾을 수가 없습니다."));

        order.update(
                orderRequestDto.getReceivingCompanyId(),
                orderRequestDto.getProductId(),
                orderRequestDto.getQuantity(),
                orderRequestDto.getDeliveryId()
        );

        Order savedOrder = orderRepository.save(order);

        return OrderResponseDto.fromOrder(savedOrder);
    }
}
