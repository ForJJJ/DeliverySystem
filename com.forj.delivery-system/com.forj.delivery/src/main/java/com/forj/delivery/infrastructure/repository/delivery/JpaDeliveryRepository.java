package com.forj.delivery.infrastructure.repository.delivery;

import com.forj.delivery.domain.model.delivery.Delivery;
import com.forj.delivery.domain.repository.DeliveryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;



public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID> , DeliveryRepository {
}
