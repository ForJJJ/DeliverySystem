package com.forj.slack_message.application.service;

import com.forj.slack_message.infrastructure.config.FeignConfig;
import com.forj.slack_message.infrastructure.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth", configuration = FeignConfig.class)
public interface UserServiceClient {
    @GetMapping("/api/v1/users/{userId}")
    UserDto getSlackIdByUserId(@PathVariable("userId") Long userId);
}
