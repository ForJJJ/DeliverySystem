package com.forj.delivery_history.application.service;

import com.forj.delivery_history.domain.model.deliveryhistory.DeliveryHistory;
import com.forj.delivery_history.domain.model.hubmovement.HubMovement;
import com.forj.delivery_history.domain.repository.DeliveryHistoryRepository;
import com.forj.delivery_history.domain.repository.HubMovementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryHistoryService {

    private final DeliveryHistoryRepository deliveryHistoryRepository;
    private final HubMovementRepository hubMovementRepository;

    private int sequenceCounter = 1;

    public void createDeliveryHistory(
            UUID deliveryAgentId,
            UUID startHubId,
            UUID endHubId
    ) {
        UUID currentHubId = startHubId;

        while (!currentHubId.equals(endHubId)) {

            HubMovement nextMovement = hubMovementRepository.findById(currentHubId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 회사는 찾을 수가 없습니다."));

            if (nextMovement == null) {
                log.error("다음 허브를 찾을 수 없습니다: 현재 허브: {}, 도착 허브: {}", currentHubId, nextMovement);
                break; // 더 이상 이동할 수 없으면 종료
            }

            // 배송 기록 생성
            DeliveryHistory deliveryHistory = DeliveryHistory.create(
                    deliveryAgentId,
                    currentHubId,
                    nextMovement.getArrivalHubId(),
                    sequenceCounter++
            );

            // 배송 기록 저장
            deliveryHistoryRepository.save(deliveryHistory);
            log.info("배송 기록이 저장되었습니다: {} -> {} (시퀀스: {})", currentHubId, nextMovement.getArrivalHubId(), deliveryHistory.getSequence());

            // 현재 허브 업데이트
            currentHubId = nextMovement.getArrivalHubId();
        }
    }
}
