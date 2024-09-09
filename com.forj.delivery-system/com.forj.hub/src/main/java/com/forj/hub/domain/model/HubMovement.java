package com.forj.hub.domain.model;

import com.forj.hub.domain.model.BaseEntity;
import com.forj.hub.domain.model.Hub;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_hub_movements")
@NoArgsConstructor
public class HubMovement extends BaseEntity {

    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "departure_hub_id")
    private Hub departureHub;
    @ManyToOne
    @JoinColumn(name = "arrival_hub_id")
    private Hub arrivalHub;
    private String duration;
    private String route;
}
