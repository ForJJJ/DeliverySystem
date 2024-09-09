package com.forj.delivery.domain.model;

import com.forj.delivery.domain.enums.DeliveryStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p_delivery")
@Builder
public class Delivery {

    @Id
    private UUID deliveryId;

    private UUID orderId;
    private DeliveryStatusEnum status;
    private UUID startHubId;
    private UUID endHubId;
//    private String endAddress;
//    private UUID deliveryAgentId;
//    private BigInteger userId;
//    private String slackId;
    private Boolean isDelete;

    // 주문 생성과 동시에 배달 생성
    public static Delivery create(
            UUID deliveryId,
            UUID orderId,
            UUID startHubId,
            UUID endHubId
//            String endAddress,
//            UUID userId

    ){
        return Delivery.builder()
                .deliveryId(deliveryId)
                .orderId(orderId)
                .status(DeliveryStatusEnum.READY)
                .startHubId(startHubId)
                .endHubId(endHubId)
//                .endAddress(endAddress)
//                .deliveryAgentId()
//                .userId(userId)
                .isDelete(false)
                .build();
    }


}
