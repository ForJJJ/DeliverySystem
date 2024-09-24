package com.forj.product.domain.model;

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
@Table(name = "p_products")
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    private UUID companyId;
    private UUID managingHubId;
    private Integer quantity;

    @Builder(access = AccessLevel.PROTECTED)
    public Product(String name, UUID companyId, UUID managingHubId, Integer quantity) {
        this.name = name;
        this.companyId = companyId;
        this.managingHubId = managingHubId;
        this.quantity = quantity;
    }

    public static Product createProduct(String name, UUID companyId, UUID managingHubId, Integer quantity) {
        return Product.builder()
                .name(name)
                .companyId(companyId)
                .managingHubId(managingHubId)
                .quantity(quantity)
                .build();
    }

    public void updateProductInfo(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void updateProductManagementHub(UUID managingHubId) {
        this.managingHubId = managingHubId;
    }

    public void reduceStockQuantity(Integer quantity) {
        this.quantity -= quantity;
    }
}
