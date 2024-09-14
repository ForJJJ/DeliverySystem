package com.forj.delivery.domain.repository;

import com.forj.delivery.domain.model.company.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    Optional<Company> findById(UUID companyId);
}
