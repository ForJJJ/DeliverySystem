package com.forj.delivery.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forj.delivery.application.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final DeliveryService deliveryService;

    @RabbitListener(queues = "${message.queue.delivery}")
    public void handleOrderCreatedEvent(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);

            // 배송 정보 생성
            deliveryService.createDelivery(
                    event.getOrderId(),
                    event.getRequestCompanyId(),
                    event.getReceivingCompanyId(),
                    event.getDeliveryId()
            );
        } catch (Exception e) {
            // 예외 처리 로직 추가 (예: 로그 기록)
            e.printStackTrace();
        }
    }
}
