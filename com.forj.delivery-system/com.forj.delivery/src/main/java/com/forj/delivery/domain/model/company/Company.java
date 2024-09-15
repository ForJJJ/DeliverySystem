package com.forj.delivery.domain.model.company;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
@Builder
public class Company {

    @Id
    @UuidGenerator
    @Column(name = "company_id", updatable = false, nullable = false)
    private UUID companyId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "company_type", length = 50)
    private String companyType;

    @Column(name = "managing_hub_id")
    private UUID managingHubId;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false; // 기본값: false
}
