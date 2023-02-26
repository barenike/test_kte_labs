package com.example.test_kte_labs.model.repository;

import com.example.test_kte_labs.model.entity.ProductRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRatingRepository extends JpaRepository<ProductRatingEntity, UUID> {
    @Query("select p from ProductRatingEntity p where p.productId = ?1")
    List<ProductRatingEntity> findByProductId(UUID id);
}
