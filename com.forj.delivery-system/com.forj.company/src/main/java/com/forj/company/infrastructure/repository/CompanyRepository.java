package com.forj.company.infrastructure.repository;

import com.forj.company.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByIdAndUserId(UUID companyId, String userId);
}
