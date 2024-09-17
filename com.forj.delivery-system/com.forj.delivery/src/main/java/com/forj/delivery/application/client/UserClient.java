package com.forj.delivery.application.client;


import com.forj.delivery.application.dto.response.UserResponseDto;
import com.forj.delivery.infrastructure.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user", configuration = FeignClientConfig.class)
public interface UserClient {

    @GetMapping("/api/v1/users/{userId}")
    UserResponseDto getUserInfo(
            @PathVariable Long userId
    );
}
