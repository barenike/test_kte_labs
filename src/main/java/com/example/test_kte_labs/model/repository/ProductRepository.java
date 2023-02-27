package com.example.test_kte_labs.model.repository;

import com.example.test_kte_labs.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    @Query("select p from ProductEntity p where p.id = ?1")
    ProductEntity findByProductId(UUID id);

    @Query("select p from ProductEntity p where p.discountRate != 0")
    List<ProductEntity> findByDiscountRateNotZero();
}
