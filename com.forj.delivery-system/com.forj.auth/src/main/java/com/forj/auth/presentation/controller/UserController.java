package com.forj.auth.presentation.controller;

import com.forj.auth.application.dto.request.DeliveryAgentRequestDto;
import com.forj.auth.application.dto.request.UserUpdateRequestDto;
import com.forj.auth.application.dto.response.DeliveryAgentGetResponseDto;
import com.forj.auth.application.dto.response.UserGetResponseDto;
import com.forj.auth.application.dto.response.UserSearchResponseDto;
import com.forj.auth.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserGetResponseDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PatchMapping("/{userId}")
    public void updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequestDto requestDto) {
        userService.updateUser(userId, requestDto);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('MASTER')")
    public Page<UserSearchResponseDto> searchUser(@RequestParam(value = "username", required = false) String usernameKeyword,
                                                  @RequestParam(value = "role", required = false) String roleKeyword,
                                                  @PageableDefault Pageable pageable) {
        return userService.searchUser(usernameKeyword, roleKeyword, pageable);
    }

    @PostMapping("/{userId}/delivery-agent/signup")
    @PreAuthorize("hasAuthority('DELIVERYAGENT')")
    public void signupDeliveryAgent(@PathVariable Long userId,
                                    @RequestBody DeliveryAgentRequestDto requestDto) {
        userService.signupDeliveryAgent(userId, requestDto);
    }

    @GetMapping("/{userId}/delivery-agent")
    @PreAuthorize("hasAuthority('DELIVERYAGENT')")
    public DeliveryAgentGetResponseDto getDeliveryAgent(@PathVariable Long userId) {
        return userService.getDeliveryAgent(userId);
    }

    @PatchMapping("/{userId}/delivery-agent")
    @PreAuthorize("hasAuthority('DELIVERYAGENT')")
    public void updateDeliveryAgent(@PathVariable Long userId, @RequestBody DeliveryAgentRequestDto requestDto) {
        userService.updateDeliveryAgent(userId, requestDto);
    }

}
