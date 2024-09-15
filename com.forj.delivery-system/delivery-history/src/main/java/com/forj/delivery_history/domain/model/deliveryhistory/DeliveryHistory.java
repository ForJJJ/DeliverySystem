package com.forj.delivery_history.domain.model.deliveryhistory;

import com.forj.delivery_history.domain.enums.DeliveryHistoryStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UuidGenerator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p_delivery_histories")
@Builder
@Slf4j
public class DeliveryHistory {

    @Id
    @UuidGenerator
    private UUID deliveryHistoryId;

    private UUID deliveryAgentId;
    private UUID startHubId;
    private UUID endHubId;
    private Integer sequence;

    @Enumerated(EnumType.STRING)
    private DeliveryHistoryStatusEnum status;

    private Duration actualTravelTime;

    private LocalDateTime createdAt;



    public static DeliveryHistory create(
            UUID deliveryAgentId,
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
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void complete(
            DeliveryHistoryStatusEnum status,
            LocalDateTime completeTime
    ) {
        this.status = status;

        // Interval 생성
        this.actualTravelTime = Duration.between(this.createdAt, completeTime);

        // 포맷팅된 문자열 생성
        String formattedTravelTime = formatTravelTime(this.actualTravelTime);
        log.info("Formatted Travel Time: " + formattedTravelTime);
    }

    public void update(
            DeliveryHistoryStatusEnum status
    ){
        this.status = status;
        this.createdAt = LocalDateTime.now();
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
