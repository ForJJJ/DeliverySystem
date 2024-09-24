package com.forj.delivery.application.dto.response;

public record UserResponseDto (
        Long userId,
        String username,
        String role,
        String slackId
){}
