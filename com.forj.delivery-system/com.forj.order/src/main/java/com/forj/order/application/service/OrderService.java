package com.forj.order.application.service;

import com.forj.order.application.dto.OrderResponseDto;
import com.forj.order.domain.model.Order;
import com.forj.order.domain.repostiory.OrderRepository;
import com.forj.order.presentation.request.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
