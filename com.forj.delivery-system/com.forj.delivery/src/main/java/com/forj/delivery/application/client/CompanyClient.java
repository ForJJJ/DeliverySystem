package com.forj.delivery.application.client;

import com.forj.delivery.application.dto.response.CompanyResponseDto;
import com.forj.delivery.infrastructure.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company", configuration = FeignClientConfig.class)
public interface CompanyClient {

    @GetMapping("/api/v1/companies/{companyId}")
    CompanyResponseDto getCompanyInfo(@PathVariable UUID companyId);
}
