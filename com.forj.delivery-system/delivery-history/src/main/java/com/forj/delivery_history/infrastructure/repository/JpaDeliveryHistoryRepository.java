package com.forj.delivery_history.infrastructure.repository;


import com.forj.delivery_history.domain.model.DeliveryHistory;
import com.forj.delivery_history.domain.repository.DeliveryHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDeliveryHistoryRepository extends JpaRepository<DeliveryHistory, UUID>, DeliveryHistoryRepository {
}
