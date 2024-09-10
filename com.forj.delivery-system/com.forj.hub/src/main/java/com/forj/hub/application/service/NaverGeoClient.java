package com.forj.hub.application.service;

import com.forj.hub.application.dto.response.NaverGeoPointResponseDto;
import com.forj.hub.infrastructure.configuration.NaverHeaderConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "naver-geo",
        url = "${feign.naver.url}",
        configuration = NaverHeaderConfig.class
)
public interface NaverGeoClient {

    @GetMapping
    NaverGeoPointResponseDto getGeoPoint(@RequestParam(value = "query") String query);
}
