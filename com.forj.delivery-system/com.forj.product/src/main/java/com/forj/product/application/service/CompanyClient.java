package com.forj.product.application.service;

import com.forj.product.application.dto.response.CompanyInfoResponseDto;
import com.forj.product.infrastructure.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company", configuration = FeignClientConfig.class)
public interface CompanyClient {

    @GetMapping("/api/v1/companies/{companyId}")
    CompanyInfoResponseDto getCompanyInfo(@PathVariable String companyId);
}
