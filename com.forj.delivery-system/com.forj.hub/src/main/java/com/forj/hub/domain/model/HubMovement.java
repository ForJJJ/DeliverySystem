package com.forj.hub.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_hub_movements")
@NoArgsConstructor
public class HubMovement extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "departure_hub_id")
    private Hub departureHub;

    @ManyToOne
    @JoinColumn(name = "arrival_hub_id")
    private Hub arrivalHub;

    private Long duration;
    private String route;

    @Builder(access = AccessLevel.PROTECTED)
    public HubMovement(Hub departureHub, Hub arrivalHub, Long duration, String route) {
        this.departureHub = departureHub;
        this.arrivalHub = arrivalHub;
        this.duration = duration;
        this.route = route;
    }

    public static HubMovement createHubMovement(Hub departureHub, Hub arrivalHub, Long duration, String route) {
        return HubMovement.builder()
                .departureHub(departureHub)
                .arrivalHub(arrivalHub)
                .duration(duration)
                .route(route)
                .build();
    }

    public void updateDuration(Long duration) {
        this.duration = duration;
    }

    public void updateHubMovementInfo(String route) {
        this.route = route;
    }
}
