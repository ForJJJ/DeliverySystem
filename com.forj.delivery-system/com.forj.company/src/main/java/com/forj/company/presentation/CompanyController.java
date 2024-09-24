package com.forj.company.presentation;

import com.forj.company.application.dto.request.CompanyHubUpdateRequestDto;
import com.forj.company.application.dto.request.CompanyRequestDto;
import com.forj.company.application.dto.response.CompanyInfoResponseDto;
import com.forj.company.application.dto.response.CompanyListResponseDto;
import com.forj.company.application.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBCOMPANY')")
    public ResponseEntity<CompanyInfoResponseDto> createCompany(
            @RequestBody CompanyRequestDto request
    ) {

        CompanyInfoResponseDto companyInfo = companyService.createCompany(request);

        return ResponseEntity.ok(companyInfo);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyInfoResponseDto> getCompanyInfo(
            @PathVariable UUID companyId
    ) {

        return ResponseEntity.ok(companyService.getCompanyInfo(companyId));
    }

    @GetMapping
    public ResponseEntity<CompanyListResponseDto> getCompaniesInfo() {

        return ResponseEntity.ok(companyService.getCompaniesInfo());
    }

    @PutMapping("/{companyId}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBCOMPANY')")
    public ResponseEntity<CompanyInfoResponseDto> updateCompanyInfo(
            @PathVariable UUID companyId,
            @RequestBody CompanyRequestDto request
    ) {

        return ResponseEntity.ok(companyService.updateCompanyInfo(companyId, request));
    }

    @PatchMapping("/{companyId}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBMASTER')")
    public ResponseEntity<CompanyInfoResponseDto> updateCompanyManagementHub(
            @PathVariable UUID companyId,
            @RequestBody CompanyHubUpdateRequestDto request
    ) {

        return ResponseEntity.ok(
                companyService.updateCompanyManagementHub(companyId, request));
    }

    @DeleteMapping("/{companyId}")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<Boolean> deleteCompany(@PathVariable UUID companyId) {

        return ResponseEntity.ok(companyService.deleteCompany(companyId));
    }
}
