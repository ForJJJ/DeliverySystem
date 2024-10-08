package com.forj.delivery.application.service;

import com.forj.common.security.SecurityUtil;
import com.forj.delivery.application.client.CompanyClient;
import com.forj.delivery.application.client.UserClient;
import com.forj.delivery.application.dto.request.DeliveryUpdateRequestDto;
import com.forj.delivery.application.dto.request.DriverAssignRequestDto;
import com.forj.delivery.application.dto.response.CompanyInfoResponseDto;
import com.forj.delivery.application.dto.response.DeliveryListResponseDto;
import com.forj.delivery.application.dto.response.DeliveryResponseDto;
import com.forj.delivery.domain.enums.DeliveryStatusEnum;
import com.forj.delivery.domain.model.Delivery;
import com.forj.delivery.domain.repository.DeliveryRepository;
import com.forj.delivery.domain.service.DeliveryDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private final UserClient userClient;

    private final CompanyClient companyClient;

    private final DeliveryDomainService deliveryDomainService;


    // 배송 생성 -> 주문이 들어옴과 동시에 생성
    public void createDelivery(
            UUID orderId,
            UUID startCompanyId,
            UUID endCompanyId,
            String userId,
            String role
    ) {
        log.info("현재 userId 값 : {}", userId);
        // 출발 허브 id 찾기
        CompanyInfoResponseDto startCompany = companyClient.getCompanyInfo(role, startCompanyId.toString());
        // 도착 허브 id 찾기
        CompanyInfoResponseDto endCompany = companyClient.getCompanyInfo(role, endCompanyId.toString());

        deliveryDomainService.create(
                orderId,
                UUID.fromString(startCompany.managingHubId()),
                UUID.fromString(endCompany.managingHubId()),
                endCompany.address(),
                Long.valueOf(userId)
        );
    }

    // 배송 조회하기
    public DeliveryResponseDto getFindById(
            UUID delivery_id
    ) {
        Delivery delivery = deliveryDomainService.findDeliveryById(delivery_id);

        return convertDeliveryToDto(delivery);
    }

    // 주문 Id로 배송 조회
    public DeliveryResponseDto getFindByOrderId(
            UUID orderId
    ) {
        Delivery delivery = deliveryDomainService.findOrderById(orderId);

        return convertDeliveryToDto(delivery);
    }

    // 배송 전체 조회
    public DeliveryListResponseDto getAllDelivery(
            Pageable pageable
    ) {
        Page<Delivery> deliveries = deliveryDomainService.findAllDelivery(pageable);

        log.info("[Delivery : DeliveryService] 주문 조회 완료");

        return new DeliveryListResponseDto(deliveries.map(this::convertDeliveryToDto));
    }

    // 배송 내용 수정 (배달 기사님 ID 정보만 수정가능)
    public DeliveryResponseDto updateDelivery(
            UUID deliveryId,
            DeliveryUpdateRequestDto requestDto
    ) {
        Delivery deliveryById = deliveryDomainService.findDeliveryById(deliveryId);

        if (!deliveryById.getCreatedBy().equals(SecurityUtil.getCurrentUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        Delivery delivery = deliveryDomainService.update(
                deliveryId,
                requestDto.deliveryAgentId()
        );
        log.info("[Delivery : DeliveryService] 배송자 수정");

        // delivery-history쪽에 큐 설정하는거 추가하기

        return convertDeliveryToDto(delivery);
    }

    // 배송 삭제
    public Boolean deleteDelivery(
            UUID deliveryId
    ) {
        deliveryDomainService.delete(deliveryId);
        log.info("[Delivery : DeliveryService] 배송 기록 삭제 완료");

        return true;
    }

    // 배송 완료시 상태 값 변경
    public void deliveryComplete(
            Long deliveryAgentId
    ) {
        log.info("Delivery Agent Id: {}", deliveryAgentId);

        deliveryDomainService.deliveryComplete(deliveryAgentId);
    }

    // 배송 기사님 여러개 배송 등록하기
    public void assignDelivery(
            DriverAssignRequestDto requestDto
    ) {
        List<UUID> deliveryIds = requestDto.deliveryIds();

        // 배송 요청 정보를 저장할 리스트
        List<Delivery> deliveriesToAssign = new ArrayList<>();

        deliveryDomainService.assignDelivery(
                Long.valueOf(SecurityUtil.getCurrentUserId()),
                deliveryIds,
                deliveriesToAssign
        );

    }

    private DeliveryResponseDto convertDeliveryToDto(Delivery delivery) {
        return new DeliveryResponseDto(
                delivery.getDeliveryId(),
                delivery.getOrderId(),
                delivery.getStatus(),
                delivery.getStartHubId(),
                delivery.getEndHubId(),
                delivery.getEndAddress(),
                delivery.getDeliveryAgentId(),
                delivery.getUserId(),
                delivery.getSlackId()
        );
    }

    public List<DeliveryResponseDto> getPendingDeliveries() {
        return deliveryRepository.findAllByStatusAndCreatedAtAfter(DeliveryStatusEnum.PENDING, LocalDateTime.now().minusDays(1).withHour(6))
                .stream()
                .map(DeliveryResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public long getDeliveriesByAgentIdAndTimeRange(Long deliveryAgentId, UUID hubId, LocalDateTime startTime, LocalDateTime endTime) {
        return deliveryRepository.countByDeliveryAgentIdAndStartHubIdAndCreatedAtBetween(deliveryAgentId, hubId, startTime, endTime);
    }

}
