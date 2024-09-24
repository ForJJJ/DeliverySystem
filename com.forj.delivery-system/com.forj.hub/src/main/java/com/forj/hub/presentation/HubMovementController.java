package com.forj.hub.presentation;

import com.forj.hub.application.dto.request.HubMovementRequestDto;
import com.forj.hub.application.dto.request.HubMovementUpdateRequestDto;
import com.forj.hub.application.dto.response.HubMovementInfoResponseDto;
import com.forj.hub.application.dto.response.HubMovementListResponseDto;
import com.forj.hub.application.service.HubMovementApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/hub-movements")
public class HubMovementController {

    private final HubMovementApplicationService hubMovementApplicationService;
    @PostMapping
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<HubMovementInfoResponseDto> createHubMovement(
            @RequestBody HubMovementRequestDto request
    ) {

        return ResponseEntity.ok(
                hubMovementApplicationService.createHubMovement(request));
    }

    @GetMapping("/{hubMovementId}")
    public ResponseEntity<HubMovementInfoResponseDto> getHubMovementInfo(
            @PathVariable UUID hubMovementId
    ) {

        return ResponseEntity.ok(
                hubMovementApplicationService.getHubMovementInfo(hubMovementId));
    }

    @GetMapping("/departure/{departureHubId}")
    public ResponseEntity<HubMovementInfoResponseDto> getHubMovementInfoByDepartureHub(
            @PathVariable UUID departureHubId
    ) {
        return ResponseEntity.ok(
                hubMovementApplicationService
                        .getHubMovementInfoByDepartureHub(departureHubId)
        );
    }


    @GetMapping
    public ResponseEntity<HubMovementListResponseDto> getHubMovementListInfo(
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                hubMovementApplicationService.getHubMovementListInfo(search, pageable)
        );
    }

    @PutMapping("/{hubMovementId}")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<HubMovementInfoResponseDto> updateHubMovementInfo(
            @PathVariable UUID hubMovementId,
            @RequestBody HubMovementUpdateRequestDto request
            ) {

        return ResponseEntity.ok(
                hubMovementApplicationService.updateHubMovementInfo(hubMovementId, request));
    }

    @PatchMapping("/{hubMovementId}")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<HubMovementInfoResponseDto> updateHubMovementDuration(
            @PathVariable UUID hubMovementId
    ) {

        return ResponseEntity.ok(
                hubMovementApplicationService.updateHubMovementDuration(hubMovementId));
    }

    @DeleteMapping("/{hubMovementId}")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<Boolean> deleteHubMovement(
            @PathVariable UUID hubMovementId
    ) {

        return ResponseEntity.ok(
                hubMovementApplicationService.deleteHubMovement(hubMovementId));
    }
}
