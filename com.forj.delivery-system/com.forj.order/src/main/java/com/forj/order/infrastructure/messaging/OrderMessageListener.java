package com.forj.order.infrastructure.messaging;

import com.forj.order.domain.service.OrderDomainService;
import com.forj.order.infrastructure.messaging.message.OrderDeliveryCompleteMessage;
import com.forj.order.infrastructure.messaging.message.OrderProductErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderMessageListener {

    private final OrderDomainService orderDomainService;

    @RabbitListener(queues = "${message.queue.complete.order}")
    public void completeOrder(
            OrderDeliveryCompleteMessage message
    ){
        log.info("[Order : OrderMessageListener] queue : complete.order에서 {} 수신 받았습니다.",message);
        orderDomainService.updateDeliveryId(
                message.orderId(),
                message.deliveryId()
        );
    }

    @RabbitListener(queues = "${message.queue.orderError}")
    public void orderError(
            OrderProductErrorMessage message
    ){
        log.info("[Order : OrderMessageListener] queue : orderError에서 {} 수신 받았습니다.",message);
        orderDomainService.cancel(UUID.fromString(message.orderId()));
    }
}
