package com.forj.delivery.infrastructure.messaging;

import com.forj.delivery.application.service.DeliveryService;
import com.forj.delivery.infrastructure.messaging.message.DeliveryDeliveryHistoryCompleteMessage;
import com.forj.delivery.infrastructure.messaging.message.DeliveryProductMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryMessageListener {

    private final DeliveryService deliveryService;

    @RabbitListener(queues = "${message.forj.queue.delivery}")
    public void handleOrderCreatedEvent(DeliveryProductMessage message) {
            // 배송 정보 생성
            deliveryService.createDelivery(
                    message.orderId(),
                    message.requestCompanyId(),
                    message.receivingCompanyId()
            );
    }

    @RabbitListener(queues = "${message.complete.queue.delivery}")
    public void listenDeliveryComplete(DeliveryDeliveryHistoryCompleteMessage message){
        // 배송이 완료된 배달기사님 아이디 전송
        log.info("Received deliveryStatusChangeEvent: {}", message);
        deliveryService.deliveryComplete(
                message.deliveryAgentId()
        );
    }
}
