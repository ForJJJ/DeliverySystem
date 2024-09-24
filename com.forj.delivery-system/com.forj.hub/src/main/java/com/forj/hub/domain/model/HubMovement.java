package com.forj.hub.domain.model;

import com.forj.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.Duration;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_hub_movements")
@NoArgsConstructor
public class HubMovement extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private UUID departureHubId;
    private UUID arrivalHubId;
    private Duration duration;
    private String route;

    @Builder(access = AccessLevel.PROTECTED)
    public HubMovement(UUID departureHubId, UUID arrivalHubId, Duration duration, String route) {
        this.departureHubId = departureHubId;
        this.arrivalHubId = arrivalHubId;
        this.duration = duration;
        this.route = route;
    }

    public static HubMovement createHubMovement(UUID departureHubId, UUID arrivalHubId, Duration duration, String route) {
        return HubMovement.builder()
                .departureHubId(departureHubId)
                .arrivalHubId(arrivalHubId)
                .duration(duration)
                .route(route)
                .build();
    }

    public void updateDuration(Duration duration) {
        this.duration = duration;
    }

    public void updateHubMovementInfo(String route) {
        this.route = route;
    }
}
