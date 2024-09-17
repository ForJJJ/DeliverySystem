package com.forj.delivery.domain.model;

import com.forj.common.jpa.BaseEntity;
import com.forj.delivery.domain.enums.DeliveryStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "p_delivery")
public class Delivery extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID deliveryId;

    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatusEnum status;

    private UUID startHubId;
    private UUID endHubId;
    private String endAddress;
    private Long deliveryAgentId;
    private Long userId;
    private String slackId;

    @Builder(access = AccessLevel.PROTECTED)
    public Delivery(
            UUID orderId,
            DeliveryStatusEnum status,
            UUID startHubId,
            UUID endHubId,
            String endAddress,
            Long deliveryAgentId,
            Long userId,
            String slackId
    ){
        this.orderId = orderId;
        this.status = status;
        this.startHubId = startHubId;
        this.endHubId = endHubId;
        this.endAddress = endAddress;
        this.deliveryAgentId = deliveryAgentId;
        this.userId = userId;
        this.slackId = slackId;
    }

    // 주문 생성과 동시에 배달 생성
    public static Delivery create(
            UUID orderId,
            UUID startHubId,
            UUID endHubId,
            String endAddress,
            Long userId,
            String slackId

    ){
        return Delivery.builder()
                .orderId(orderId)
                .status(DeliveryStatusEnum.PENDING)
                .startHubId(startHubId)
                .endHubId(endHubId)
                .endAddress(endAddress)
                .userId(userId)
                .slackId(slackId)
                .build();
    }

    // 배송자 수정
    public void update(
            Long deliveryAgentId
    ){
        this.deliveryAgentId = deliveryAgentId;
    }

    public void assignDeliveryAgentId(
            Long deliveryAgentId
    ) {
        this.deliveryAgentId = deliveryAgentId;
    }

    public void updateStatus(
            DeliveryStatusEnum status
    ){
        this.status = status;
    }


}
