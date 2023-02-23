package com.example.test_kte_labs.model.repository;

import com.example.test_kte_labs.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}
