package com.forj.hub.domain.model;

import com.forj.common.jpa.BaseEntity;
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

    @OneToMany(mappedBy = "departureHubId")
    private List<HubMovement> departureHubs;

    @OneToMany(mappedBy = "arrivalHubId")
    private List<HubMovement> arrivalHubs;

    @Builder(access = AccessLevel.PROTECTED)
    public Hub(UUID id, String name, String address, double longitude, double latitude) {
        this.id = id;
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

    public static Hub toHubEntityWithId(UUID id, String name, String address, double longitude, double latitude) {
        return Hub.builder()
                .id(id)
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
