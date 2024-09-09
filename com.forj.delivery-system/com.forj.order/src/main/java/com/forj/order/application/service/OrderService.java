package com.forj.order.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forj.order.infrastructure.messaging.OrderCreatedEvent;
import com.forj.order.application.dto.response.OrderResponseDto;
import com.forj.order.domain.enums.OrderStatusEnum;
import com.forj.order.domain.model.Order;
import com.forj.order.domain.repostiory.OrderRepository;
import com.forj.order.application.dto.request.OrderRequestDto;
import com.forj.order.application.dto.request.OrderStatusRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;

    @Value("${message.queue.delivery}")
    private String deliveryQueue;

    private final RabbitTemplate rabbitTemplate;

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

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getOrderId(),
                UUID.fromString(userId),
                savedOrder.getReceivingCompanyId(),
                savedOrder.getDeliveryId()
        );
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jsonString != null) {
            rabbitTemplate.convertAndSend(deliveryQueue, jsonString);
        }

        return OrderResponseDto.fromOrder(savedOrder);
    }
    // 주문 생성

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
    // 주문 내역 삭제
    @Transactional
    public void deleteOrder(
            UUID orderId
    ) {
        Order order = orderRepository.findById(orderId)
                .filter(o -> !o.getIsdelete())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 주문은 찾을 수가 없습니다."));

        order.setIsdelete(true);

        orderRepository.save(order);
    }

    // 주문 상태 변경하기
    @Transactional
    public void updateOrderStatus(
            UUID orderId,
            OrderStatusRequestDto requestDto
    ){
        Order order = orderRepository.findById(orderId)
                .filter(o -> !o.getIsdelete())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 주문은 찾을 수가 없습니다."));

        order.setStatus(OrderStatusEnum.valueOf(requestDto.getStatus()));

        orderRepository.save(order);
    }

    // 주문 취소 요청하기
    @Transactional
    public void cancelOrder(
            UUID orderId
    ){
        Order order = orderRepository.findById(orderId)
                .filter(o -> !o.getIsdelete())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 주문은 찾을 수가 없습니다."));
        if(!order.getStatus().equals(OrderStatusEnum.PROGRESS)){
            throw new IllegalArgumentException("이미 취소되었거나 주문완료가 된 상태입니다.");
        }

        order.cancelOrder();
    }
}
