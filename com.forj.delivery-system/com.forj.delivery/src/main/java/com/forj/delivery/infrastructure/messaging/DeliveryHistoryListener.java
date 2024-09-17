package com.forj.delivery.infrastructure.messaging;

import com.forj.delivery.application.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryHistoryListener {

    private final DeliveryService deliveryService;

    @RabbitListener(queues = "${message.queue.complete.delivery}")
    public void listenDeliveryComplete(DeliveryStatusChangeEvent event){
        // 배송이 완료된 배달기사님 아이디 전송
        log.info("Received deliveryStatusChangeEvent: {}", event);
        deliveryService.deliveryComplete(
                event.getDeliveryAgentId()
        );
    }
}
