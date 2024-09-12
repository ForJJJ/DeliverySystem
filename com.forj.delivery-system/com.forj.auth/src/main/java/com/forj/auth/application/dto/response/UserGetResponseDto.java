package com.forj.auth.application.dto.response;

import com.forj.auth.domain.model.User;
import com.forj.auth.domain.model.UserRole;
import lombok.Builder;

@Builder
public record UserGetResponseDto(

        Long userId,
        String username,
        UserRole role

) {

    public static UserGetResponseDto fromEntity(User user) {
        return UserGetResponseDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

}
