package com.forj.hub.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_hubs")
@NoArgsConstructor
public class Hub extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    private String address;
    private double longitude;
    private double latitude;

    @OneToMany(mappedBy = "departureHub")
    private List<HubMovement> departureHubs;

    @OneToMany(mappedBy = "arrivalHub")
    private List<HubMovement> arrivalHubs;

    @Builder(access = AccessLevel.PROTECTED)
    public Hub(String name, String address, double longitude, double latitude) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static Hub toHubEntity(String name, String address, double longitude, double latitude) {
        return Hub.builder()
                .name(name)
                .address(address)
                .longitude(longitude)
                .latitude(latitude)
                .build();
    }

    public void updateHub(String name, String address, double longitude, double latitude) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
