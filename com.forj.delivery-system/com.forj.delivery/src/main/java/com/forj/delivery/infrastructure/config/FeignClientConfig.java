package com.forj.delivery.infrastructure.config;

import com.forj.common.security.SecurityUtil;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        return template -> {
            String userId = SecurityUtil.getCurrentUserId();
            String roles = SecurityUtil.getCurrentUserRoles();

            if (userId != null) {
                template.header("X-User-Id", userId);
            }
            if (roles != null) {
                template.header("X-User-Role", roles);
            }
        };
    }
}

