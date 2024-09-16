package com.forj.product.domain.service;

import com.forj.common.security.SecurityUtil;
import com.forj.product.domain.model.Product;
import com.forj.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product createProduct(
            String name, String productId, String managingHubId, Integer quantity
    ) {

        Product product = Product.createProduct(
                name, productId, managingHubId, quantity);

        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product getProductInfo(String productId) {

        return getProduct(productId);
    }

    @Transactional(readOnly = true)
    public Page<Product> getProductsInfo(Pageable pageable) {

        return productRepository.findAll(pageable);
    }

    @Transactional
    public Product updateProductInfo(Product product, String name, Integer quantity) {

        product.updateProductInfo(name, quantity);

        return product;
    }

    @Transactional
    public Product updateProductManagementHub(String productId, String hubId) {

        Product product = getProduct(productId);

        product.updateProductManagementHub(hubId);

        return product;
    }

    @Transactional
    public void deleteProduct(String productId) {

        Product product = getProduct(productId);

        product.delete(SecurityUtil.getCurrentUserId());
    }

    @Transactional
    public void reduceProductQuantity(String productId, Integer quantity) {

        Product product = getProduct(productId);
        log.info("Product quantity : {}", product.getQuantity());

        if (product.getQuantity() < quantity) {
            log.info("INSUFFICIENT_STOCK_ERROR { "
                    + "productId : " + productId
                    + " request : " + quantity
                    + " stock : " + product.getQuantity() + " }");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "INSUFFICIENT STOCK ERROR");
        }

        product.reduceStockQuantity(quantity);
    }

    private Product getProduct(String productId) {
        return productRepository.findById(UUID.fromString(productId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
