package com.forj.delivery_history.domain.model.hubmovement;

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
public class HubMovement {

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
