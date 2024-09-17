package com.forj.delivery.domain.model.delivery;

import com.forj.delivery.domain.enums.DeliveryStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

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
    @UuidGenerator
    private UUID deliveryId;

    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatusEnum status;
    private UUID startHubId;
    private UUID endHubId;
    private String endAddress;
    private UUID deliveryAgentId;
    private BigInteger userId;
//    private String slackId;
    private Boolean isDelete;

    // 주문 생성과 동시에 배달 생성
    public static Delivery create(
            UUID orderId,
            UUID startHubId,
            UUID endHubId,
            String endAddress
//            BigInteger userId

    ){
        return Delivery.builder()
                .orderId(orderId)
                .status(DeliveryStatusEnum.READY)
                .startHubId(startHubId)
                .endHubId(endHubId)
                .endAddress(endAddress)
//                .userId(userId)
                .isDelete(false)
                .build();
    }

    public void setDeliveryAgentId(UUID deliveryAgentId) {
        this.deliveryAgentId = deliveryAgentId;
    }

    public void setStatus(DeliveryStatusEnum status){ this.status = status; }


}
