package com.forj.auth.presentation.controller;

import com.forj.auth.application.dto.request.UserSignupRequestDto;
import com.forj.auth.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid UserSignupRequestDto requestDto) {
        userService.signup(requestDto);
    }

}
