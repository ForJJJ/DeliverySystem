package com.forj.auth.presentation.controller;

import com.forj.auth.application.dto.request.UserUpdateRequestDto;
import com.forj.auth.application.dto.response.UserGetResponseDto;
import com.forj.auth.application.dto.response.UserSearchResponseDto;
import com.forj.auth.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<UserGetResponseDto> getUser(@PathVariable Long userId) {
        UserGetResponseDto user = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequestDto requestDto) {
        userService.updateUser(userId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<Page<UserSearchResponseDto>> searchUser(@RequestParam(value = "username", required = false) String usernameKeyword,
                                                  @RequestParam(value = "role", required = false) String roleKeyword,
                                                  @PageableDefault Pageable pageable) {
        Page<UserSearchResponseDto> users = userService.searchUser(usernameKeyword, roleKeyword, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

}
