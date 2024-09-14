package com.forj.delivery_history.infrastructure.messaging;

import com.forj.delivery_history.application.service.DeliveryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryEventListener {

    private final DeliveryHistoryService deliveryHistoryService;

    @RabbitListener(queues = "${message.queue.delivery-history}")
    public void hadleDeliveryEvent(DeliveryCreatedEvent event){
        deliveryHistoryService.createDeliveryHistory(
                event.getDeliveryAgentId(),
                event.getStartHubId(),
                event.getEndHubId()
        );
    }
}
