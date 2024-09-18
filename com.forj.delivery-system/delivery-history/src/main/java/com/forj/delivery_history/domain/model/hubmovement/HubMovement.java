package com.forj.delivery_history.domain.model.hubmovement;

import com.forj.common.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_hub_movements")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HubMovement extends BaseEntity {

    @Id
    private UUID id;

    @Column(name = "departure_hub_id")
    private UUID departureHubId;

    @Column(name = "arrival_hub_id")
    private UUID arrivalHubId;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "route")
    private String route;
}
