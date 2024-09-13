package com.forj.company.domain.model;

import com.forj.common.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String userId;
    private String managingHubId;
    private String address;
    private CompanyType companyType;
}
