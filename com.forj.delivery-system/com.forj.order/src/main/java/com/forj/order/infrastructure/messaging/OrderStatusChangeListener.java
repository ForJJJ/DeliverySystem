package com.forj.order.infrastructure.messaging;

import com.forj.order.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusChangeListener {

    private final OrderService orderService;

    @RabbitListener(queues = "${message.queue.complete.order}")
    public void completeOrder(
            OrderStatusChangeEvent event
    ){
        orderService.updateDeliveryId(
                event.getOrderId(),
                event.getDeliveryId()
        );
    }
}
