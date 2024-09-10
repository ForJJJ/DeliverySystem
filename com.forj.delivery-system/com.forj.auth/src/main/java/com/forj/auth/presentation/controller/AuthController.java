package com.forj.auth.presentation.controller;

import com.forj.auth.application.dto.request.UserLoginRequestDto;
import com.forj.auth.application.dto.request.UserSignupRequestDto;
import com.forj.auth.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid UserSignupRequestDto requestDto) {
        userService.signup(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserLoginRequestDto requestDto) {
        String token = userService.login(requestDto);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .build();
    }

}
