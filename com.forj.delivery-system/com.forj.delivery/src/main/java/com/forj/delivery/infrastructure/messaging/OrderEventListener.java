package com.forj.delivery.infrastructure.messaging;

import com.forj.delivery.application.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final DeliveryService deliveryService;

    @RabbitListener(queues = "${message.queue.delivery}")
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
            // 배송 정보 생성
            deliveryService.createDelivery(
                    event.getOrderId(),
                    event.getRequestCompanyId(),
                    event.getReceivingCompanyId(),
                    event.getDeliveryId()
            );
    }
}
