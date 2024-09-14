package com.forj.delivery_history.domain.model.deliveryhistory;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p_delivery_histories")
@Builder
public class DeliveryHistory {

    @Id
    @UuidGenerator
    private UUID deliveryHistoryId;

    private UUID deliveryAgentId;
    private UUID startHubId;
    private UUID endHubId;
    private Integer sequence;


    public static DeliveryHistory create(
            UUID deliveryAgentId,
            UUID startHubId,
            UUID endHubId,
            Integer sequence
    ){
        return DeliveryHistory.builder()
                .deliveryAgentId(deliveryAgentId)
                .startHubId(startHubId)
                .endHubId(endHubId)
                .sequence(sequence)
                .build();
    }
}
