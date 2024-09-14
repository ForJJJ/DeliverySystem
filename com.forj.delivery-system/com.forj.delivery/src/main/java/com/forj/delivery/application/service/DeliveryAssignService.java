package com.forj.delivery.application.service;

import com.forj.delivery.application.dto.request.DriverAssignRequestDto;
import com.forj.delivery.domain.model.delivery.Delivery;
import com.forj.delivery.domain.repository.DeliveryRepository;
import com.forj.delivery.infrastructure.messaging.DeliveryCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // 추가
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j // 추가
public class DeliveryAssignService {

    private final DeliveryRepository deliveryRepository;

    @Value("${message.queue.delivery-history}")
    private String deliveryHistoryQueue;

    private final RabbitTemplate rabbitTemplate;

    public void assignDriver(
            String userId,
            DriverAssignRequestDto requestDto
    ){
        UUID deliveryAgentId = UUID.fromString(userId);
        List<UUID> deliveryIds = requestDto.getDeliveryIds();

        // 배송 요청 정보를 저장할 리스트
        List<Delivery> deliveriesToAssign = new ArrayList<>();

        log.info("배송 기사 ID: {}", deliveryAgentId);
        log.info("할당할 배송 ID 목록: {}", deliveryIds);

        for (UUID deliveryId : deliveryIds) {
            log.info("배송 ID: {}", deliveryId);
            Delivery delivery = deliveryRepository.findById(deliveryId)
                    .filter(o -> o.getDeliveryAgentId() == null)
                    .orElseThrow(() -> {
                        log.error("배송 ID {}를 찾을 수 없거나 이미 할당되었습니다.", deliveryId);
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 배송은 찾을 수가 없거나 배송이 잡혔습니다.");
                    });

            log.info("배송 {}에 기사 ID {}를 할당합니다.", deliveryId, deliveryAgentId);
            delivery.setDeliveryAgentId(deliveryAgentId);
            // 배송 상태 업데이트
            // delivery.setStatus("ASSIGNED"); // 필요에 따라 주석 해제

            // 변경 사항 저장
            deliveriesToAssign.add(delivery);
            deliveryRepository.save(delivery);
            log.info("배송 ID {}가 성공적으로 업데이트되었습니다.", deliveryId);
        }

        if (!deliveriesToAssign.isEmpty()) {
            DeliveryCreatedEvent event = new DeliveryCreatedEvent(
                    deliveryAgentId,
                    deliveriesToAssign.get(0).getStartHubId(), // 첫 번째 배송의 시작 허브 ID
                    deliveriesToAssign.get(0).getEndHubId()    // 첫 번째 배송의 종료 허브 ID
            );

            rabbitTemplate.convertAndSend(deliveryHistoryQueue, event);
            log.info("배송 생성 이벤트가 큐 {}에 발행되었습니다.", deliveryHistoryQueue);
        } else {
            log.warn("할당할 배송이 없습니다.");
        }
    }
}
