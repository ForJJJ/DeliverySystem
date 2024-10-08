package com.forj.delivery_agent.domain.model;

import com.forj.common.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name = "p_delivery_agents")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DeliveryAgent extends BaseEntity {

    @Id
    @Column(name = "delivery_agent_id")
    private Long deliveryAgentId;

    @Column(name = "hub_id")
    private UUID hubId;

    @Enumerated(EnumType.STRING)
    private DeliveryAgentRole agentRole;

    public static DeliveryAgent signupDeliveryAgent(Long deliveryAgentId, UUID hubId, DeliveryAgentRole agentRole) {
        return DeliveryAgent.builder()
                .deliveryAgentId(deliveryAgentId)
                .hubId(hubId)
                .agentRole(agentRole)
                .build();
    }

    public void updateDeliveryAgent(UUID hubId, DeliveryAgentRole agentRole) {
        if (hubId != null) {
            this.hubId = hubId;
        }

        if (agentRole != null) {
            this.agentRole = agentRole;
        }
    }

}
