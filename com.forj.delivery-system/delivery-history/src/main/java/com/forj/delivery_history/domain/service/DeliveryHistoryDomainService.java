package com.forj.delivery_history.domain.service;

import com.forj.delivery_history.application.client.HubMovementClient;
import com.forj.delivery_history.application.dto.HubMovementInfoResponseDto;
import com.forj.delivery_history.domain.enums.DeliveryHistoryStatusEnum;
import com.forj.delivery_history.domain.model.DeliveryHistory;
import com.forj.delivery_history.domain.repository.DeliveryHistoryRepository;
import com.forj.delivery_history.infrastructure.messaging.DeliveryHistoryMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryHistoryDomainService {

    private final DeliveryHistoryRepository deliveryHistoryRepository;
    private final DeliveryHistoryMessageProducer deliveryHistoryMessageProducer;
    private final HubMovementClient hubMovementClient;

    @Transactional
    public void create(
            Long deliveryAgentId,
            UUID startHubId,
            UUID endHubId,
            String role
    ){
        UUID currentHubId = startHubId;
        int sequenceCounter = 1;

        while (!currentHubId.equals(endHubId)) {
            HubMovementInfoResponseDto nextMovement = hubMovementClient.getHubMovementInfo(currentHubId,role);


            if (nextMovement == null) {
                log.error("다음 허브를 찾을 수 없습니다: 현재 허브: {}, 도착 허브: {}", currentHubId, nextMovement);
                break; // 더 이상 이동할 수 없으면 종료
            }
            DeliveryHistoryStatusEnum status = DeliveryHistoryStatusEnum.READY;

            if (sequenceCounter == 1) {
                status = DeliveryHistoryStatusEnum.PROGRESS;
            }

            // 배송 기록 생성
            DeliveryHistory deliveryHistory = DeliveryHistory.create(
                    deliveryAgentId,
                    currentHubId,
                    nextMovement.arrivalHubId(),
                    sequenceCounter++,
                    status
            );

            // 배송 기록 저장
            deliveryHistoryRepository.save(deliveryHistory);
            log.info("배송 기록이 저장되었습니다: {} -> {} (시퀀스: {})", currentHubId, nextMovement.arrivalHubId(), deliveryHistory.getSequence());

            // 현재 허브 업데이트
            currentHubId = nextMovement.arrivalHubId();
        }
    }


    public void arrive(
            Long userId
    ){
        DeliveryHistory currentDelivery = deliveryHistoryRepository.findByDeliveryAgentIdAndStatus(userId, DeliveryHistoryStatusEnum.PROGRESS )
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 기사님는 찾을 수가 없습니다."));
        // 상태값을 COMPLETE로 변경
        currentDelivery.complete(
                DeliveryHistoryStatusEnum.COMPLETED,
                LocalDateTime.now(),
                currentDelivery.getUpdatedAt());

        // 변경된 엔티티를 저장
        deliveryHistoryRepository.save(currentDelivery);

        DeliveryHistory nextDelivery = deliveryHistoryRepository.findFirstByDeliveryAgentIdAndStatus(userId, DeliveryHistoryStatusEnum.READY )
                .orElseThrow(() -> {
                    // 배송이 완료된 상태라면 상태 변경 이벤트 발행
                    deliveryHistoryMessageProducer.sendToDelivery(userId);
                    return new ResponseStatusException(HttpStatus.OK, "배송이 완료되었습니다.");
                });

        nextDelivery.update(DeliveryHistoryStatusEnum.PROGRESS);


        // 변경된 엔티티를 저장
        deliveryHistoryRepository.save(nextDelivery);

    }
}
