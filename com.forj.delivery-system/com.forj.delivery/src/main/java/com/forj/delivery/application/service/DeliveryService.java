package com.forj.delivery.application.service;

import com.forj.delivery.domain.model.Delivery;
import com.forj.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public void createDelivery(
            UUID deliveryId,
            UUID orderId,
            UUID startHubId,
            UUID endHubId
    ) {
        Delivery delivery = Delivery.create(
                deliveryId,
                orderId,
                startHubId,
                endHubId
        );

        deliveryRepository.save(delivery);
    }

}
