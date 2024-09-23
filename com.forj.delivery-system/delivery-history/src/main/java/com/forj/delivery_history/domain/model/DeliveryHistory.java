package com.forj.delivery_history.domain.model;

import com.forj.common.jpa.BaseEntity;
import com.forj.delivery_history.domain.enums.DeliveryHistoryStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UuidGenerator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "p_delivery_histories")
@Slf4j
public class DeliveryHistory extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID deliveryHistoryId;

    private Long deliveryAgentId;
    private UUID startHubId;
    private UUID endHubId;
    private Integer sequence;

    @Enumerated(EnumType.STRING)
    private DeliveryHistoryStatusEnum status;
    private Duration actualTravelTime;

    @Builder(access = AccessLevel.PROTECTED)
    public DeliveryHistory(
            UUID deliveryHistoryId,
            Long deliveryAgentId,
            UUID startHubId,
            UUID endHubId,
            Integer sequence,
            DeliveryHistoryStatusEnum status,
            Duration actualTravelTime
    ){
        this.deliveryHistoryId = deliveryHistoryId;
        this.deliveryAgentId = deliveryAgentId;
        this.startHubId = startHubId;
        this.endHubId = endHubId;
        this.sequence = sequence;
        this.status = status;
        this.actualTravelTime = actualTravelTime;
    }


    public static DeliveryHistory create(
            Long deliveryAgentId,
            UUID startHubId,
            UUID endHubId,
            Integer sequence,
            DeliveryHistoryStatusEnum status
    ){
        return DeliveryHistory.builder()
                .deliveryAgentId(deliveryAgentId)
                .startHubId(startHubId)
                .endHubId(endHubId)
                .sequence(sequence)
                .status(status)
                .build();
    }

    public void complete(
            DeliveryHistoryStatusEnum status,
            LocalDateTime completeTime,
            LocalDateTime updateTime
    ) {
        this.status = status;

        // Interval 생성
        this.actualTravelTime = Duration.between(updateTime, completeTime);

        // 포맷팅된 문자열 생성
        String formattedTravelTime = formatTravelTime(this.actualTravelTime);
        log.info("Formatted Travel Time: " + formattedTravelTime);
    }

    public void update(
            DeliveryHistoryStatusEnum status
    ){
        this.status = status;
    }

    // Duration을 포맷팅하는 메서드
    public String formatTravelTime(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        if (hours > 0) {
            return String.format("%d시간 %d분 %d초", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%d분 %d초", minutes, seconds);
        } else {
            return String.format("%d초", seconds);
        }
    }
}
