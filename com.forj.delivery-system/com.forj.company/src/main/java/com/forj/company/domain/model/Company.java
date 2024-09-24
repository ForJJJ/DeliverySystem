package com.forj.company.domain.model;

import com.forj.common.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_companies")
@NoArgsConstructor
public class Company extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    private Long userId;
    private UUID managingHubId;
    private String address;
    private CompanyType companyType;

    @Builder(access = AccessLevel.PROTECTED)
    public Company(String name, Long userId, UUID managingHubId, String address, CompanyType companyType) {
        this.name = name;
        this.userId = userId;
        this.managingHubId = managingHubId;
        this.address = address;
        this.companyType = companyType;
    }

    public static Company createCompany(String name, Long userId, UUID managingHubId, String address, CompanyType companyType) {
        return Company.builder()
                .name(name)
                .userId(userId)
                .managingHubId(managingHubId)
                .address(address)
                .companyType(companyType)
                .build();
    }

    public void updateCompanyInfo(String name, String address, CompanyType companyType) {
        this.name = name;
        this.address = address;
        this.companyType = companyType;
    }

    public void updateCompanyManagementHub(UUID managingHubId) {
        this.managingHubId = managingHubId;
    }
}
