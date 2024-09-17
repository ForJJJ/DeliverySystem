package com.forj.order.application.service;

import com.forj.common.security.SecurityUtil;
import com.forj.order.application.dto.response.OrderListResponseDto;
import com.forj.order.domain.service.OrderDomainService;
import com.forj.order.infrastructure.messaging.OrderMessageProducer;
import com.forj.order.application.dto.response.OrderResponseDto;
import com.forj.order.domain.enums.OrderStatusEnum;
import com.forj.order.domain.model.Order;
import com.forj.order.domain.repostiory.OrderRepository;
import com.forj.order.application.dto.request.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

// 기본적인 CRUD
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderMessageProducer orderProductMessage;
    private final OrderDomainService orderDomainService;

    // 주문 생성
    public OrderResponseDto createOrder(
            OrderRequestDto orderRequestDto
    ) {
        Order order = orderDomainService.create(
                orderRequestDto.requestCompanyId(),
                orderRequestDto.receivingCompanyId(),
                orderRequestDto.productId(),
                orderRequestDto.quantity()
        );
        log.info("[Order : OrderService] 주문 생성완료");
        // product 쪽으로 메시징 큐 전달
        orderProductMessage.sendToProduct(
                order.getOrderId(),
                order.getRequestCompanyId(),
                order.getReceivingCompanyId(),
                order.getProductId(),
                order.getQuantity()
        );

        return convertOrderToDto(order);
    }

    // 주문 단건 조회
    public OrderResponseDto getOrderById(
            UUID orderId
    ) {
        Order order = orderDomainService.findOrderById(
                orderId
        );
        log.info("[Order : OrderService] 주문 단건 조회 완료");
        return convertOrderToDto(order);
    }

    // 주문 전체 조회
    public OrderListResponseDto getAllOrder(
            Pageable pageable
    ){
        Page<Order> orders = orderDomainService.findAllOrder(pageable);

        log.info("[Order : OrderService] 주문 조회 완료");

        return new OrderListResponseDto(orders.map(this::convertOrderToDto));
    }

    // 주문 내용 수정
    public OrderResponseDto updateOrder(
            UUID orderId,
            OrderRequestDto orderRequestDto
    ) {
        Order orderById = orderDomainService.findOrderById(orderId);

        if (!orderById.getCreatedBy().equals(SecurityUtil.getCurrentUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
        }

        Order order = orderDomainService.update(
                orderId,
                orderRequestDto.receivingCompanyId(),
                orderRequestDto.productId(),
                orderRequestDto.quantity()
        );
        log.info("[Order : OrderService] 주문 내용 수정");

        // product 쪽으로 메시징 큐 전달
        orderProductMessage.sendToProduct(
                order.getOrderId(),
                order.getRequestCompanyId(),
                order.getReceivingCompanyId(),
                order.getProductId(),
                order.getQuantity()
        );

        return convertOrderToDto(order);
    }

    // 주문 내역 삭제
    public Boolean deleteOrder(
            UUID orderId
    ) {
        orderDomainService.delete(orderId);
        log.info("[Order : OrderService] 주문 삭제 완료");

        return true;
    }

    // 주문 취소 요청하기
    public void cancelOrder(
            UUID orderId
    ){
        Order order = orderDomainService.cancel(orderId);

        if(!order.getStatus().equals(OrderStatusEnum.PROGRESS)){
            log.info("[Order : OrderService] 주문 취소 실패 현재 상태 : {}",order.getStatus());
            throw new IllegalArgumentException("이미 취소되었거나 주문완료가 된 상태입니다.");
        }
        log.info("[Order : OrderService] 주문 취소 요청");

        orderProductMessage.sendToProductCancel(
                order.getOrderId(),
                order.getProductId(),
                order.getQuantity()
        );
    }

    private OrderResponseDto convertOrderToDto(Order order) {
        return new OrderResponseDto(
                order.getOrderId(),
                order.getRequestCompanyId(),
                order.getReceivingCompanyId(), // 수정된 부분
                order.getProductId(),
                order.getQuantity(),
                order.getDeliveryId(),
                order.getStatus()
        );
    }
}
