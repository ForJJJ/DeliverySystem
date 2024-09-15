package com.forj.company.application.service;

import com.forj.company.application.dto.response.NaverGeoPointResponseDto;
import com.forj.company.infrastructure.configuration.NaverHeaderConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "naver-geo",
        url = "${feign.naver.url}",
        configuration = NaverHeaderConfig.class
)
public interface NaverGeoClient {

    @GetMapping("/map-geocode/v2/geocode")
    NaverGeoPointResponseDto getGeoPoint(@RequestParam(value = "query") String query);
}
