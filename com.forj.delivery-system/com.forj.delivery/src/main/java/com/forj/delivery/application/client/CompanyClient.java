package com.forj.delivery.application.client;

import com.forj.delivery.application.dto.response.CompanyInfoResponseDto;
import com.forj.delivery.infrastructure.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "company",url = "http://localhost:19095", configuration = FeignClientConfig.class)
public interface CompanyClient {

    @GetMapping("/api/v1/companies/{companyId}")
    CompanyInfoResponseDto getCompanyInfo(@PathVariable String companyId);
}
