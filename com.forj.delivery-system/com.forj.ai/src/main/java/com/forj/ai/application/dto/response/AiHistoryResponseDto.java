package com.forj.ai.application.dto.response;

import com.forj.ai.domain.model.Ai;
import com.forj.ai.domain.model.RequestType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AiHistoryResponseDto(

        UUID aiId,
        Long deliveryAgentId,
        RequestType requestType,
        String aiRequest,
        String aiResponse

) {

    public static AiHistoryResponseDto fromEntity(Ai ai) {
        return AiHistoryResponseDto.builder()
                .aiId(ai.getAiId())
                .deliveryAgentId(ai.getDeliveryAgentId())
                .requestType(ai.getRequestType())
                .aiRequest(ai.getAiRequest())
                .aiResponse(ai.getAiResponse())
                .build();
    }

}
