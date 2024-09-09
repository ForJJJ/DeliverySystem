package com.forj.hub.presentation;

import com.forj.hub.application.dto.HubCreateResponseDto;
import com.forj.hub.application.dto.HubRequestDto;
import com.forj.hub.application.dto.HubResponseDto;
import com.forj.hub.application.service.HubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubs")
@Slf4j
public class HubController {

    private final HubService hubService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MASTER')")
    public ResponseEntity<HubCreateResponseDto> createHub(@RequestBody HubRequestDto request) {
        log.info("HubCreateRequestDto : {}, {}",request.name(), request.address());

        return ResponseEntity.ok(hubService.createHub(request));
    }

    @GetMapping("/{hubId}")
    public ResponseEntity<HubResponseDto> getHubInfo(@PathVariable String hubId) {

        return ResponseEntity.ok(hubService.getHubInfo(hubId));
    }

    // TODO Search

    @PatchMapping("/{hubId}")
    @PreAuthorize("hasAuthority('ROLE_MASTER')")
    public ResponseEntity<HubResponseDto> updateHubInfo(
            @PathVariable String hubId,
            @RequestBody HubRequestDto request
            ) {
        return ResponseEntity.ok(hubService.updateHubInfo(hubId, request));
    }

    @DeleteMapping("/{hubId}")
    @PreAuthorize("hasAuthority('ROLE_MASTER')")
    public ResponseEntity<Boolean> deleteHub(
            @PathVariable String hubId
    ) {
        return ResponseEntity.ok(hubService.deleteHub(hubId));
    }
}
