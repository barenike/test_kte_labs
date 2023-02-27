package com.example.test_kte_labs.model.repository;

import com.example.test_kte_labs.model.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, UUID> {
    List<OrderDetailEntity> findByProductId(UUID id);

    List<OrderDetailEntity> findByOrderId(UUID id);

    @Query("select distinct o.orderId from OrderDetailEntity o where o.productId = ?1")
    List<UUID> getDistinctOrderIdListByProductId(UUID id);
}
