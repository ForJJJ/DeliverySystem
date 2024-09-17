package com.forj.order.infrastructure.messaging;

import com.forj.order.infrastructure.messaging.message.OrderProductCancelMessage;
import com.forj.order.infrastructure.messaging.message.OrderProductMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderMessageProducer {

    @Value("${message.forj.queue.product}")
    private String productQueue;

    @Value("${message.cancel.queue.product}")
    private String cancelProductQueue;

    private final RabbitTemplate rabbitTemplate;

    // 전송 큐
    public void sendToProduct(
            UUID orderId,
            UUID requestCompanyId,
            UUID receivingCompanyId,
            UUID productId,
            Integer quantity
    ){
        OrderProductMessage message = new OrderProductMessage(
                orderId,
                requestCompanyId,
                receivingCompanyId,
                productId,
                quantity
        );

        rabbitTemplate.convertAndSend(productQueue,message);
    }

    // 취소 큐
    public void sendToProductCancel(
            UUID orderId,
            UUID productId,
            Integer quantity
    ){
        OrderProductCancelMessage message = new OrderProductCancelMessage(
                orderId,
                productId,
                quantity
        );

        rabbitTemplate.convertAndSend(cancelProductQueue,message);
    }
}
