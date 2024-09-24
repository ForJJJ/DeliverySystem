package com.forj.delivery_agent.application.service;

import com.forj.delivery_agent.application.dto.request.DeliveryAgentRequestDto;
import com.forj.delivery_agent.application.dto.response.DeliveryAgentGetResponseDto;
import com.forj.delivery_agent.domain.model.DeliveryAgent;
import com.forj.delivery_agent.domain.model.DeliveryAgentRole;
import com.forj.delivery_agent.domain.repository.DeliveryAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryAgentService {

    private final DeliveryAgentRepository deliveryAgentRepository;

    @Transactional
    public void signupDeliveryAgent(Long deliveryAgentId, DeliveryAgentRequestDto requestDto) {
        if (deliveryAgentRepository.existsByDeliveryAgentId(deliveryAgentId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 배달담당자로 등록되어있습니다.");
        }

        DeliveryAgent deliveryAgent = DeliveryAgent.signupDeliveryAgent(deliveryAgentId, requestDto.hubId(), requestDto.role());

        deliveryAgentRepository.save(deliveryAgent);
    }

    public DeliveryAgentGetResponseDto getDeliveryAgent(Long deliveryAgentId) {
        DeliveryAgent deliveryAgent = deliveryAgentRepository.findByDeliveryAgentId(deliveryAgentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "배송담당자가 아닙니다."));

        return DeliveryAgentGetResponseDto.fromEntity(deliveryAgent);
    }

    @Transactional
    public void updateDeliveryAgent(Long userId, DeliveryAgentRequestDto requestDto) {
        DeliveryAgent deliveryAgent = verifyByDeliveryAgentId(userId);

        deliveryAgent.updateDeliveryAgent(requestDto.hubId(), requestDto.role());
    }

    public List<DeliveryAgentGetResponseDto> getAllDeliveryAgentsByCompanyRole() {
        List<DeliveryAgent> deliveryAgents = deliveryAgentRepository.findByAgentRole(DeliveryAgentRole.COMPANYDELIVERY);
        return deliveryAgents.stream()
                .map(DeliveryAgentGetResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    private DeliveryAgent verifyByDeliveryAgentId(Long deliveryAgentId) {
        DeliveryAgent deliveryAgent = deliveryAgentRepository.findByDeliveryAgentId(deliveryAgentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "배송담당자가 아닙니다."));

        if (!deliveryAgent.getDeliveryAgentId().equals(getCurrentUserId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인의 정보만 접근 가능합니다.");
        }

        return deliveryAgent;
    }

    private Long getCurrentUserId() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
