package com.forj.hub.infrastructure.configuration;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverHeaderConfig {

    @Value("${feign.naver.id}")
    private String naverClientId;

    @Value("${feign.naver.secret}")
    private String naverSecret;

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> requestTemplate
                        .header("X-NCP-APIGW-API-KEY-ID", naverClientId)
                        .header("X-NCP-APIGW-API-KEY", naverSecret);
    }
}
