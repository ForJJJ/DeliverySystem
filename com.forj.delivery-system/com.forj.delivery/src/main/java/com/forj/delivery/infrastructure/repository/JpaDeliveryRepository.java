package com.forj.delivery.infrastructure.repository;

import com.forj.delivery.domain.model.Delivery;
import com.forj.delivery.domain.repository.DeliveryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;



public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID> , DeliveryRepository {
}
