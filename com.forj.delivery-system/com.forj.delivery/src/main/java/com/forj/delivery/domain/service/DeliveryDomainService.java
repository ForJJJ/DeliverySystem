package com.forj.delivery.domain.service;

import com.forj.common.security.SecurityUtil;
import com.forj.delivery.domain.enums.DeliveryStatusEnum;
import com.forj.delivery.domain.model.Delivery;
import com.forj.delivery.domain.repository.DeliveryRepository;
import com.forj.delivery.infrastructure.messaging.DeliveryMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryDomainService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMessageProducer deliveryMessageProducer;

    // 배송 생성
    @Transactional
    public void create(
            UUID orderId,
            UUID startHubId,
            UUID endHubId,
            String endAddress,
            Long userId
    ){
        Delivery delivery = Delivery.create(
                orderId,
                startHubId,
                endHubId,
                endAddress,
                userId
        );

        log.info("[Delivery : DeliveryDomainService] 배송 생성");

        deliveryRepository.save(delivery);
    }

    // 배송 단건 조회
    @Transactional(readOnly = true)
    public Delivery findDeliveryById(
            UUID deliveryId
    ) {
        Delivery delivery = getDelivery(deliveryId);
        log.info("[Delivery : DeliveryDomainService] 배송 단건 조회");
        return delivery;
    }

    // 주문 Id로 배송 조회
    @Transactional(readOnly = true)
    public Delivery findOrderById(
            UUID orderId
    ){
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                        .filter(d -> !d.isDeleted())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 배송은 찾을 수가 없습니다."));
        log.info("[Delivery : DeliveryDomainService] 주문 Id로 배송 조회");
        return delivery;
    }

    // 배송 전체 조회
    @Transactional(readOnly = true)
    public Page<Delivery> findAllDelivery(
            Pageable pageable
    ){
        log.info("[Delivery : DeliveryDomainService] 배송 전체 조회");
        return deliveryRepository.findAll(pageable);
    }

    // 배송 내용 수정
    @Transactional
    public Delivery update(
            UUID deliveryId,
            Long deliveryAgentId
    ){
        Delivery delivery = getDelivery(deliveryId);
        log.info("[Delivery : DeliveryDomainService] 배송 내용 수정");

        if (!delivery.getStatus().equals(DeliveryStatusEnum.PENDING)){
            log.info("[Delivery : DeliveryDomainService] 배송 수정 불가 현재 상태 : {}",delivery.getStatus());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"해당 배송 수정할 수 없습니다.");
        }

        delivery.update(
            deliveryAgentId
        );

        return deliveryRepository.save(delivery);
    }
    // 배송 삭제
    @Transactional
    public void delete(
            UUID deliveryId
    ){
        Delivery delivery = getDelivery(deliveryId);
        log.info("[Delivery : DeliveryDomainService] 배송 삭제");

        if (!delivery.getStatus().equals(DeliveryStatusEnum.COMPLETED)){
            log.info("[Delivery : DeliveryDomainService] 배송 삭제 불가 현재 상태 : {}",delivery.getStatus());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"해당 배송 삭제할 수 없습니다.");
        }

        delivery.delete(SecurityUtil.getCurrentUserId());
    }

    // 배송 완료시 상태 값 변경
    @Transactional
    public void deliveryComplete(
            Long deliveryAgentId
    ){
        List<Delivery> deliveries = deliveryRepository.findByDeliveryAgentId(deliveryAgentId);

        if (deliveries.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 기사님의 배송건은 없습니다.");
        }

        // 상태 값 변경
        deliveries.forEach(delivery -> {
            if (delivery.getStatus() == DeliveryStatusEnum.PROGRESS) {
                delivery.updateStatus(DeliveryStatusEnum.COMPLETED);
                deliveryRepository.save(delivery);
            }
        });
    }

    // 배송기사님의 배송 등록
    @Transactional
    public void assignDelivery(
            Long deliveryAgentId,
            List<UUID> deliveryIds,
            List<Delivery> deliveriesToAssign
    ){
        for (UUID deliveryId : deliveryIds) {
            log.info("배송 ID: {}", deliveryId);
            Delivery delivery = deliveryRepository.findById(deliveryId)
                    .filter(o -> o.getDeliveryAgentId() == null)
                    .orElseThrow(() -> {
                        log.error("배송 ID {}를 찾을 수 없거나 이미 할당되었습니다.", deliveryId);
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 배송은 찾을 수가 없거나 배송이 잡혔습니다.");
                    });

            log.info("배송 {}에 기사 ID {}를 할당합니다.", deliveryId, deliveryAgentId);
            delivery.assignDeliveryAgentId(deliveryAgentId);
            // 배송 상태 업데이트
            delivery.updateStatus(DeliveryStatusEnum.PROGRESS);

            // 변경 사항 저장
            deliveriesToAssign.add(delivery);
            deliveryRepository.save(delivery);
            log.info("배송 ID {}가 성공적으로 업데이트되었습니다.", deliveryId);

            // 주문쪽 상태 변경 메시징 큐
            deliveryMessageProducer.sendToOrderComplete(
                    deliveryId,
                    delivery.getOrderId()
            );
        }

        if (!deliveriesToAssign.isEmpty()) {
            deliveryMessageProducer.sendToDeliveryHistroy(
                    deliveryAgentId,
                    deliveriesToAssign.get(0).getStartHubId(), // 첫 번째 배송의 시작 허브 ID
                    deliveriesToAssign.get(0).getEndHubId()    // 첫 번째 배송의 종료 허브 ID
            );
        } else {
            log.warn("할당할 배송이 없습니다.");
        }
    }

    // 조회
    private Delivery getDelivery(
            UUID deliveryId
    ) {
        return deliveryRepository.findById(deliveryId)
                .filter(d -> !d.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 배송은 찾을 수가 없습니다."));
    }
}
