package com.forj.company.application.service;

import com.forj.common.security.SecurityUtil;
import com.forj.company.application.dto.request.CompanyHubUpdateRequestDto;
import com.forj.company.application.dto.request.CompanyRequestDto;
import com.forj.company.application.dto.response.*;
import com.forj.company.domain.model.Company;
import com.forj.company.domain.model.CompanyType;
import com.forj.company.infrastructure.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private static final int EARTH_RADIUS = 6371;

    private final NaverGeoClient naverGeoClient;
    private final HubClient hubClient;
    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyInfoResponseDto createCompany(CompanyRequestDto request) {
        // Naver 좌표 가져오기
        NaverGeoPointResponseDto geoPoint = getAddressWithGeoPoint(request.address());

        // 모든 허브 정보 가져오기
        HubListResponseDto hubsInfo = hubClient.getHubsInfo("true");

        double x = Double.parseDouble(geoPoint.addresses().get(0).x());
        double y = Double.parseDouble(geoPoint.addresses().get(0).y());

        // 제일 가까운 허브 계산하기
        HubInfoResponseDto nearestHub = findNearestHub(hubsInfo, y, x);

        Company company = Company.createCompany(
                request.name(),
                SecurityUtil.getCurrentUserId(),
                nearestHub.id(),
                geoPoint.addresses().get(0).roadAddress(),
                CompanyType.fromString(request.companyType())
        );

        Company savedCompany = companyRepository.save(company);

        return convertCompanyToDto(savedCompany);
    }

    public CompanyInfoResponseDto getCompanyInfo(String companyId) {

        Company company = getCompany(companyId);

        return convertCompanyToDto(company);
    }

    public CompanyListResponseDto getCompaniesInfo() {

        List<Company> companies = companyRepository.findAll();

        return new CompanyListResponseDto(
                companies.stream().map(this::convertCompanyToDto).toList());
    }

    @Transactional
    public CompanyInfoResponseDto updateCompanyInfo(
            String companyId, CompanyRequestDto request
    ) {

        Company company = getCompanyByRole(companyId);

        company.updateCompanyInfo(
                request.name(),
                request.address(),
                CompanyType.fromString(request.companyType())
        );

        return convertCompanyToDto(company);
    }

    @Transactional
    public CompanyInfoResponseDto updateCompanyManagementHub(
            String companyId, CompanyHubUpdateRequestDto request
    ) {

        Company company = getCompany(companyId);

        company.updateCompanyManagementHub(request.hubId());

        return convertCompanyToDto(company);
    }

    @Transactional
    public Boolean deleteCompany(String companyId) {

        Company company = getCompany(companyId);

        company.delete(SecurityUtil.getCurrentUserId());

        return true;
    }

    private NaverGeoPointResponseDto getAddressWithGeoPoint(String address) {

        try {
            NaverGeoPointResponseDto geoPointResponseDto =
                    naverGeoClient.getGeoPoint(address);
            log.info("status : {}, errorMessage : {}",
                    geoPointResponseDto.status(),
                    geoPointResponseDto.errorMessage()
            );
            return geoPointResponseDto;
        } catch (Exception e) {
            log.warn("NaverClientError : {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private HubInfoResponseDto findNearestHub(
            HubListResponseDto hubListResponseDto, double companyY, double companyX
    ) {
        HubInfoResponseDto nearestHub = null;
        double minDistance = Double.MAX_VALUE;

        // hubList에서 각 HubInfoResponseDto의 좌표를 추출하여 비교
        for (HubInfoResponseDto hubInfo : hubListResponseDto.hubList()) {
            double distance = calculateDistance(
                    companyY, companyX, hubInfo.y(), hubInfo.x()
            );
            if (distance < minDistance) {
                minDistance = distance;
                nearestHub = hubInfo;
            }

            log.info("hubId {} - distance : {}", hubInfo.id(), distance);
        }

        if (nearestHub == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return nearestHub;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 위도와 경도를 라디안 값으로 변환
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        // 하버사인 공식 적용
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        log.info("두 좌표 간의 거리(km) : {}", EARTH_RADIUS * c);

        return EARTH_RADIUS * c;
    }

    private Company getCompany(String companyId) {
        return companyRepository.findById(UUID.fromString(companyId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private Company getCompanyByRole(String companyId) {

        String currentUserRole = SecurityUtil.getCurrentUserRoles();

        if (currentUserRole == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        switch (currentUserRole) {
            case "MASTER" -> {
                return getCompany(companyId);
            }
            case "HUBCOMPANY" -> {
                return companyRepository.findByIdAndUserId(
                                UUID.fromString(companyId), SecurityUtil.getCurrentUserId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private CompanyInfoResponseDto convertCompanyToDto(Company company) {
        return new CompanyInfoResponseDto(
                company.getId().toString(),
                company.getName(),
                company.getUserId(),
                company.getManagingHubId(),
                company.getAddress(),
                company.getCompanyType().name()
        );
    }
}
