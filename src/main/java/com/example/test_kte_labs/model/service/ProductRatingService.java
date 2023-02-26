package com.example.test_kte_labs.model.service;

import com.example.test_kte_labs.model.entity.ProductRatingEntity;
import com.example.test_kte_labs.model.repository.ProductRatingRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class ProductRatingService {
    private final ProductRatingRepository productRatingRepository;

    public ProductRatingService(ProductRatingRepository productRatingRepository) {
        this.productRatingRepository = productRatingRepository;
    }

    public List<ProductRatingEntity> getProductRatingList(String id) {
        return productRatingRepository.findByProductId(UUID.fromString(id));
    }

    public Double getAverageRating(String id) {
        List<ProductRatingEntity> productRatingList = getProductRatingList(id);
        OptionalDouble average = productRatingList
                .stream()
                .mapToDouble(ProductRatingEntity::getRating)
                .average();

        Double averageRating = average.isPresent() ? average.getAsDouble() : 0;
        DecimalFormat f = new DecimalFormat("##.0");

        return Double.valueOf(f.format(averageRating));
    }

    public Map<Integer, Integer> getRatingMap(String id) {
        List<ProductRatingEntity> productRatingList = getProductRatingList(id);
        Map<Integer, Integer> ratingMap = new HashMap<>() {{
            put(1, 0);
            put(2, 0);
            put(3, 0);
            put(4, 0);
            put(5, 0);
        }};
        for (ProductRatingEntity productRating : productRatingList) {
            ratingMap.put(productRating.getRating(), ratingMap.get(productRating.getRating()) + 1);
        }
        return ratingMap;
    }
}
