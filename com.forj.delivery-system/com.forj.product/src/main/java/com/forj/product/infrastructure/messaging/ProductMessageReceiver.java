package com.forj.product.infrastructure.messaging;

import com.forj.product.application.service.ProductApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class ProductMessageReceiver {

    private final ProductApplicationService productApplicationService;
    private final ProductMessageProducer productMessageProducer;

    @RabbitListener(queues = "${message.forj.queue.product}")
    public void reduceProductQuantity(ProductOrderMessage message) {
        try {
            productApplicationService.reduceProductQuantity(
                    message.productId(),
                    message.quantity()
            );

            productMessageProducer.reduceQuantitySuccessNotifier(message);

        } catch (ResponseStatusException e) {
            productMessageProducer.rollbackToOrder(
                    message,
                    e.getMessage()
            );
        }
    }
}
