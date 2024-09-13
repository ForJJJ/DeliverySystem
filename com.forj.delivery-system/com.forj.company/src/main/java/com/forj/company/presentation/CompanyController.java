package com.forj.company.presentation;

import com.forj.company.application.dto.request.CompanyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {

    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody CompanyRequestDto request) {
        return null;
    }
}
