package com.forj.delivery.infrastructure.repository.company;

import com.forj.delivery.domain.model.company.Company;
import com.forj.delivery.domain.repository.CompanyRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface JpaCompanyRepository extends JpaRepository<Company, UUID>, CompanyRepository {

}
