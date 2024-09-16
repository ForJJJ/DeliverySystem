package com.forj.product.presentation;

import com.forj.product.application.dto.request.ProductHubUpdateRequestDto;
import com.forj.product.application.dto.request.ProductRequestDto;
import com.forj.product.application.dto.response.ProductInfoResponseDto;
import com.forj.product.application.dto.response.ProductListResponseDto;
import com.forj.product.application.service.ProductApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductApplicationService productApplicationService;
    @PostMapping
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBCOMPANY')")
    public ResponseEntity<ProductInfoResponseDto> createProduct(
            @RequestBody ProductRequestDto request
    ) {

        return ResponseEntity.ok(productApplicationService.createProduct(request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductInfoResponseDto> getProductInfo(
            @PathVariable String productId
    ) {

        return ResponseEntity.ok(
                productApplicationService.getProductInfo(productId));
    }

    @GetMapping
    public ResponseEntity<ProductListResponseDto> getProductsInfo(Pageable pageable) {

        return ResponseEntity.ok(
                productApplicationService.getProductsInfo(pageable));
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBCOMPANY')")
    public ResponseEntity<ProductInfoResponseDto> updateProductInfo(
            @PathVariable String productId,
            @RequestBody ProductRequestDto request
    ) {

        return ResponseEntity.ok(
                productApplicationService.updateProductInfo(productId, request));
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUBMASTER')")
    public ResponseEntity<ProductInfoResponseDto> updateProductManagementHub(
            @PathVariable String productId,
            @RequestBody ProductHubUpdateRequestDto request
    ) {

        return ResponseEntity.ok(
                productApplicationService.updateProductManagementHub(productId, request));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable String productId) {

        return ResponseEntity.ok(productApplicationService.deleteProduct(productId));
    }
}
