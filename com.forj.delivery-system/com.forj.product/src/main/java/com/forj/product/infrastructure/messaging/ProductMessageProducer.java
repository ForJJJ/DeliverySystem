package com.forj.product.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMessageProducer {

    @Value("${messaging.queues.orderError}")
    private String productErrorQueue;

    @Value("${messaging.queues.delivery}")
    private String deliveryQueue;

    private final RabbitTemplate rabbitTemplate;

    public void reduceQuantitySuccessNotifier(
            ProductDeliveryMessage productDeliveryMessage
    ) {
        rabbitTemplate.convertAndSend(deliveryQueue, productDeliveryMessage);
    }

    public void rollbackToOrder(
            ProductDeliveryMessage productDeliveryMessage, String errorMessage
    ) {

        ProductDeliveryMessage productErrorMessage = new ProductDeliveryMessage(
                productDeliveryMessage.productId(),
                productDeliveryMessage.companyId(),
                productDeliveryMessage.managingHubId(),
                productDeliveryMessage.quantity(),
                errorMessage
        );

        rabbitTemplate.convertAndSend(productErrorQueue, productErrorMessage);
    }
}
