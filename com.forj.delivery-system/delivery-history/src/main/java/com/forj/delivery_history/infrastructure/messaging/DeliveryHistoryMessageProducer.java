package com.forj.delivery_history.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryHistoryMessageProducer {

    @Value("${message.complete.queue.delivery}")
    private String completeDeliveryQueue;

    private final RabbitTemplate rabbitTemplate;

    public void sendToDelivery(
            Long userId
    ){
        DeliveryHistoryDeliveryCompleteMessage message = new DeliveryHistoryDeliveryCompleteMessage(userId);
        rabbitTemplate.convertAndSend(completeDeliveryQueue, message);
    }
}
