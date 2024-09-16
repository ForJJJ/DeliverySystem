package com.forj.auth.application.dto.response;

import com.forj.auth.domain.model.User;
import com.forj.auth.domain.model.UserRole;
import lombok.Builder;

@Builder
public record UserSearchResponseDto(

        Long userId,
        String username,
        UserRole role,
        String slackId

) {

    public static UserSearchResponseDto fromEntity(User user) {
        return UserSearchResponseDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .role(user.getRole())
                .slackId(user.getSlackId())
                .build();
    }

}
