package com.forj.delivery_history.infrastructure.messaging;

import com.forj.delivery_history.application.service.DeliveryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryEventListener {

    private final DeliveryHistoryService deliveryHistoryService;

    @RabbitListener(queues = "${message.forj.queue.delivery-history}")
    public void hadleDeliveryEvent(DeliveryHistoryDeliveryMessage message){
        deliveryHistoryService.createDeliveryHistory(
                message.deliveryAgentId(),
                message.startHubId(),
                message.endHubId()
        );
    }
}
