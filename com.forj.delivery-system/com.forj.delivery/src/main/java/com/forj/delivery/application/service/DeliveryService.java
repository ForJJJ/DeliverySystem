package com.forj.delivery.application.service;

import com.forj.delivery.application.dto.response.DeliveryResponseDto;
import com.forj.delivery.domain.enums.DeliveryStatusEnum;
import com.forj.delivery.domain.model.company.Company;
import com.forj.delivery.domain.model.delivery.Delivery;
import com.forj.delivery.domain.repository.CompanyRepository;
import com.forj.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final CompanyRepository companyRepository;

    // 배송 생성 -> 주문이 들어옴과 동시에 생성
    public void createDelivery(
            UUID orderId,
            UUID startCompanyId,
            UUID endCompanyId,
            UUID deliveryId
    ) {

        // 출발 허브 id 찾기
        Company startCompany = companyRepository.findById(startCompanyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 회사는 찾을 수가 없습니다."));
        // 도착 허브 id 찾기
        Company endCompany = companyRepository.findById(endCompanyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 회사는 찾을 수가 없습니다."));

        // 추후에 배달 기사님 매핑 기능 구현하기
        Delivery delivery = Delivery.create(
                orderId,
                startCompany.getManagingHubId(),
                endCompany.getManagingHubId(),
                endCompany.getAddress()
//                endCompany.getUserId()
        );
        deliveryRepository.save(delivery);
    }

    // 배송 조회하기
    public DeliveryResponseDto getfindById(
            UUID delivery_id
    ) {
        Delivery delivery = deliveryRepository.findById(delivery_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 배송은 찾을 수가 없습니다."));
        return DeliveryResponseDto.fromDelivery(delivery);
    }

    // 배송 완료시 상태 값 변경
    public void deliveryComplete(
            UUID deliveryAgentId
    ){
        log.info("Delivery Agent Id: {}", deliveryAgentId);
        // deliveryAgentId로 배송 검색
        List<Delivery> deliveries = deliveryRepository.findByDeliveryAgentId(deliveryAgentId);

        if (deliveries.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 배송은 찾을 수가 없습니다.");
        }

        // 상태를 COMPLETED로 변경
        deliveries.forEach(delivery -> {
            if (delivery.getStatus() == DeliveryStatusEnum.PROGRESS) {
                delivery.setStatus(DeliveryStatusEnum.COMPLETED);
                deliveryRepository.save(delivery);
            }
        });
    }
}
