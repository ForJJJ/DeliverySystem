package com.forj.auth.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    // 인가 테스트용 메서드(삭제 예정)
    @GetMapping("/check")
    @PreAuthorize("hasAuthority('MASTER')")
    public String checkAuth() {
        System.out.println("check!");
        return "MASTER";
    }

}
