package com.forj.product.application.service;

import com.forj.common.security.SecurityUtil;
import com.forj.product.application.dto.request.ProductHubUpdateRequestDto;
import com.forj.product.application.dto.request.ProductRequestDto;
import com.forj.product.application.dto.response.CompanyInfoResponseDto;
import com.forj.product.application.dto.response.HubInfoResponseDto;
import com.forj.product.application.dto.response.ProductInfoResponseDto;
import com.forj.product.application.dto.response.ProductListResponseDto;
import com.forj.product.domain.model.Product;
import com.forj.product.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductApplicationService {


    private final ProductService productService;
    private final CompanyClient companyClient;
    private final HubClient hubClient;

    public ProductInfoResponseDto createProduct(ProductRequestDto request) {

        CompanyInfoResponseDto companyInfo =
                companyClient.getCompanyInfo(request.companyId());

        Product product = productService.createProduct(
                request.name(),
                companyInfo.id(),
                companyInfo.managingHubId(),
                request.quantity()
        );

        return convertProductToDto(product);
    }

    public ProductInfoResponseDto getProductInfo(String productId) {

        Product product = productService.getProductInfo(productId);

        return convertProductToDto(product);
    }

    public ProductListResponseDto getProductsInfo(Pageable pageable) {

        Page<Product> products = productService.getProductsInfo(pageable);

        return new ProductListResponseDto(products.map(this::convertProductToDto));
    }

    public ProductInfoResponseDto updateProductInfo(
            String productId, ProductRequestDto request
    ) {

        Product productInfo = productService.getProductInfo(productId);

        if (!productInfo.getCompanyId().equals(request.companyId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!productInfo.getCreatedBy().equals(SecurityUtil.getCurrentUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Product product = productService.updateProductInfo(
                productInfo, request.name(), request.quantity());

        return convertProductToDto(product);
    }

    public ProductInfoResponseDto updateProductManagementHub(
            String productId, ProductHubUpdateRequestDto request
    ) {

        String serverRequest = "true";

        HubInfoResponseDto hubInfo =
                hubClient.getHubInfo(request.hubId(), serverRequest);

        Product product =
                productService.updateProductManagementHub(productId, hubInfo.id());

        return convertProductToDto(product);
    }

    public Boolean deleteProduct(String productId) {

        productService.deleteProduct(productId);

        return true;
    }

    public void reduceProductQuantity(String productId, Integer quantity) {

        productService.reduceProductQuantity(productId, quantity);
    }

    private ProductInfoResponseDto convertProductToDto(Product product) {
        return new ProductInfoResponseDto(
                product.getId().toString(),
                product.getName(),
                product.getCompanyId(),
                product.getManagingHubId(),
                product.getQuantity()
        );
    }
}
