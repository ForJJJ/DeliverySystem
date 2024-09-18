package com.forj.ai.domain.model;

import com.forj.common.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "p_ais")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Ai extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ai_id")
    private UUID aiId;

    private Long deliveryAgentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestType requestType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String aiRequest;

    @Column(columnDefinition = "TEXT")
    private String aiResponse;

    public static Ai createRequestHistory(Long deliveryAgentId, RequestType requestType, String aiRequest, String aiResponse) {
        return Ai.builder()
                .deliveryAgentId(deliveryAgentId)
                .requestType(requestType)
                .aiRequest(aiRequest)
                .aiResponse(aiResponse)
                .build();
    }

}
