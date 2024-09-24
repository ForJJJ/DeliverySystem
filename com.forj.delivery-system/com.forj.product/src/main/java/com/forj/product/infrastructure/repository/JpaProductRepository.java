package com.forj.product.infrastructure.repository;

import com.forj.product.domain.model.Product;
import com.forj.product.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<Product, UUID>, ProductRepository {
}
