package com.forj.delivery_history.infrastructure.repository.deliveryhistory;


import com.forj.delivery_history.domain.model.deliveryhistory.DeliveryHistory;
import com.forj.delivery_history.domain.repository.DeliveryHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDeliveryHistoryRepository extends JpaRepository<DeliveryHistory, UUID>, DeliveryHistoryRepository {
}
