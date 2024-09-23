package com.forj.delivery_history.application.service;

import com.forj.common.security.SecurityUtil;
import com.forj.delivery_history.domain.service.DeliveryHistoryDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryHistoryService {

    private final DeliveryHistoryDomainService deliveryHistoryDomainService;

    public void createDeliveryHistory(
            Long deliveryAgentId,
            UUID startHubId,
            UUID endHubId,
            String role
    ) {
        deliveryHistoryDomainService.create(
                deliveryAgentId,
                startHubId,
                endHubId,
                role
        );

    }

    // 배송 도착 처리
    public void arrivedHub(){

        deliveryHistoryDomainService.arrive(
                Long.valueOf(SecurityUtil.getCurrentUserId())
        );
    }
}
