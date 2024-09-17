package com.forj.delivery.infrastructure.messaging;

import com.forj.delivery.infrastructure.messaging.message.DeliveryDeliveryHistoryMessage;
import com.forj.delivery.infrastructure.messaging.message.DeliveryOrderMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryMessageProducer {

    @Value("${message.queue.delivery-history}")
    private String deliveryHistoryQueue;

    @Value("${message.queue.complete.order}")
    private String completeOrderQueue;

    private final RabbitTemplate rabbitTemplate;

    public void sendToOrderComplete(
            UUID deliveryId,
            UUID orderId
    ){
        DeliveryOrderMessage message = new DeliveryOrderMessage(
                deliveryId,
                orderId
        );

        rabbitTemplate.convertAndSend(completeOrderQueue,message);

        log.info("배송 생성 이벤트가 큐 {}에 발행되었습니다.", completeOrderQueue);
    }

    public void sendToDeliveryHistroy(
            Long deliveryAgentId,
            UUID startHubId,
            UUID endHubId
    ){
        DeliveryDeliveryHistoryMessage message = new DeliveryDeliveryHistoryMessage(
                deliveryAgentId,
                startHubId,
                endHubId
        );

        rabbitTemplate.convertAndSend(deliveryHistoryQueue, message);

        log.info("배송 생성 이벤트가 큐 {}에 발행되었습니다.", deliveryHistoryQueue);
    }
}
