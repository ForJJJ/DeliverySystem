package com.forj.hub.presentation;

import com.forj.hub.application.dto.response.HubInfoResponseDto;
import com.forj.hub.application.dto.response.HubListResponseDto;
import com.forj.hub.application.dto.request.HubRequestDto;
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

    /**
     * 허브 생성
     * <p>마스터 권한만 접근 가능<p/>
     * @param request 허브 생성에 필요한 데이터
     * @return {@link HubInfoResponseDto} 객체
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MASTER')")
    public ResponseEntity<HubInfoResponseDto> createHub(@RequestBody HubRequestDto request) {
        log.info("HubCreateRequestDto : {}, {}",request.name(), request.address());

        return ResponseEntity.ok(hubService.createHub(request));
    }

    /**
     * 허브 조회
     * <p>모든 사용자 조회 가능 - 마스터 및 서버 요청인 경우 private 정보를 포함하여 응답, 그 외 public 정보만 응답에 포함<p/>
     * @param hubId 조회 하려는 허브 ID
     * @param serverRequest 서버 요청 여부
     * @return {@link HubInfoResponseDto} 객체
     */
    @GetMapping("/{hubId}")
    public ResponseEntity<HubInfoResponseDto> getHubInfo(
            @PathVariable String hubId,
            @RequestHeader(value = "X-Server-Request", required = false) String serverRequest
    ) {

        boolean isMasterOrServer = "true".equals(serverRequest) || hasMasterAuthority();

        return ResponseEntity.ok(hubService.getHubInfo(hubId, isMasterOrServer));
    }

    /**
     * 모든 허브 조회
     * <p>모든 사용자 조회 가능 - 마스터 및 서버 요청인 경우 private 정보를 포함하여 응답, 그 외 public 정보만 응답에 포함<p/>
     * @param serverRequest 서버 요청 여부
     * @return {@link HubListResponseDto} 객체 - List
     */
    @GetMapping
    public ResponseEntity<HubListResponseDto> getHubsInfo(
            @RequestHeader(value = "X-Server-Request", required = false) String serverRequest
    ) {

        boolean isMasterOrServer = "true".equals(serverRequest) || hasMasterAuthority();

        return ResponseEntity.ok(hubService.getHubsInfo(isMasterOrServer));
    }

    /**
     * 허브 수정
     * <p>마스터 권한만 접근 가능<p/>
     * @param hubId 수정하려는 허브 ID
     * @param request 수정하려는 허브 데이터
     * @return {@link HubInfoResponseDto} 객체
     */
    @PatchMapping("/{hubId}")
    @PreAuthorize("hasAuthority('ROLE_MASTER')")
    public ResponseEntity<HubInfoResponseDto> updateHubInfo(
            @PathVariable String hubId,
            @RequestBody HubRequestDto request
            ) {
        return ResponseEntity.ok(hubService.updateHubInfo(hubId, request));
    }

    /**
     * 허브 삭제 (비활성화)
     * <p>마스터 권한만 접근 가능<p/>
     * @param hubId 삭제하려는 허브 ID
     * @return {@link Boolean}
     */
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
