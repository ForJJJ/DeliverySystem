package com.forj.delivery_history.infrastructure.config;

import com.forj.common.security.SecurityUtil;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
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
