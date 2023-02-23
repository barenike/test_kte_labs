package com.example.test_kte_labs.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "order_details")
public class OrderDetailEntity {
    @Id
    @Column(unique = true, name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "subtotal_price", nullable = false)
    private Integer subtotalPrice;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "total_discount_rate", nullable = false)
    private Integer totalDiscountRate;
}
