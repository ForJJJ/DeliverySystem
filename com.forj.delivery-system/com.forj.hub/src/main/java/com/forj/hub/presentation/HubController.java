package com.forj.hub.presentation;

import com.forj.hub.application.dto.HubInfoResponseDto;
import com.forj.hub.application.dto.HubListResponseDto;
import com.forj.hub.application.dto.HubRequestDto;
import com.forj.hub.application.service.HubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubs")
@Slf4j
public class HubController {

    private final HubService hubService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MASTER')")
    public ResponseEntity<HubInfoResponseDto> createHub(@RequestBody HubRequestDto request) {
        log.info("HubCreateRequestDto : {}, {}",request.name(), request.address());

        return ResponseEntity.ok(hubService.createHub(request));
    }

    @GetMapping("/{hubId}")
    public ResponseEntity<HubInfoResponseDto> getHubInfo(
            @PathVariable String hubId,
            @RequestHeader(value = "X-Server-Request", required = false) String serverRequest
    ) {

        boolean isMasterOrServer = "true".equals(serverRequest) || hasMasterAuthority();

        return ResponseEntity.ok(hubService.getHubInfo(hubId, isMasterOrServer));
    }

    @GetMapping
    public ResponseEntity<HubListResponseDto> getHubsInfo(
            @RequestHeader(value = "X-Server-Request", required = false) String serverRequest
    ) {

        boolean isMasterOrServer = "true".equals(serverRequest) || hasMasterAuthority();

        return ResponseEntity.ok(hubService.getHubsInfo(isMasterOrServer));
    }

    @PatchMapping("/{hubId}")
    @PreAuthorize("hasAuthority('ROLE_MASTER')")
    public ResponseEntity<HubInfoResponseDto> updateHubInfo(
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

    private boolean hasMasterAuthority() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_MASTER"));
    }
}
