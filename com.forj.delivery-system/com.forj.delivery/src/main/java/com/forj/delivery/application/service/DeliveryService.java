package com.forj.delivery.application.service;

import com.forj.delivery.application.dto.response.DeliveryResponseDto;
import com.forj.delivery.domain.model.Delivery;
import com.forj.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    // 배송 생성 -> 주문이 들어옴과 동시에 생성
    public void createDelivery(
            UUID deliveryId,
            UUID orderId,
            UUID startHubId,
            UUID endHubId
    ) {
        Delivery delivery = Delivery.create(
                deliveryId,
                orderId,
                startHubId,
                endHubId
        );
        // 추후에 회사 아이디를 HubId로 바꾸는 기능 구현하기
        // 기사님 배차 시스템은 어떻게 하지?? 기사님이 잡는 형식으로 가야하나?? VS 자동으로 배차하는 시스템으로 가야하나??
        deliveryRepository.save(delivery);
    }

    public DeliveryResponseDto getfindById(
            UUID delivery_id
    ) {
        Delivery delivery = deliveryRepository.findById(delivery_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 배송은 찾을 수가 없습니다."));
        return DeliveryResponseDto.fromDelivery(delivery);
    }

}
