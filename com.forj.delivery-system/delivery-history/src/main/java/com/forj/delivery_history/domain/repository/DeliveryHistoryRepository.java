package com.forj.delivery_history.domain.repository;

import com.forj.delivery_history.domain.enums.DeliveryHistoryStatusEnum;
import com.forj.delivery_history.domain.model.deliveryhistory.DeliveryHistory;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryHistoryRepository {
    DeliveryHistory save(DeliveryHistory deliveryHistory);
    Optional<DeliveryHistory> findById(UUID deliveryHistoryId);


    Optional<DeliveryHistory> findFirstByDeliveryAgentIdAndStatus(Long deliveryAgentId, DeliveryHistoryStatusEnum status);
}
