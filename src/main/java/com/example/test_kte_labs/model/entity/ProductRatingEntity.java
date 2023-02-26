package com.example.test_kte_labs.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Data
@Entity
@Table(name = "product_ratings")
public class ProductRatingEntity {
    @Id
    @Column(unique = true, name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Range(min = 1, max = 5)
    @Column(name = "rating", nullable = false)
    private Integer rating;
}
