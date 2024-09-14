package com.forj.delivery_history.domain.repository;

import com.forj.delivery_history.domain.model.deliveryhistory.DeliveryHistory;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryHistoryRepository {
    DeliveryHistory save(DeliveryHistory deliveryHistory);
    Optional<DeliveryHistory> findById(UUID deliveryHistoryId);
}
