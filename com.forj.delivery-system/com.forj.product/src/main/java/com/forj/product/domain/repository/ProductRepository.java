package com.forj.product.domain.repository;

import com.forj.product.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(UUID uuid);
}
