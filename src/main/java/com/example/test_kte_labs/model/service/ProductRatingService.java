package com.example.test_kte_labs.model.service;

import com.example.test_kte_labs.exceptions.ClientHasAlreadyRatedThisProductException;
import com.example.test_kte_labs.exceptions.ClientHasNotRatedThisProductException;
import com.example.test_kte_labs.infrastructure.product.ProductExtraInfoResponse;
import com.example.test_kte_labs.infrastructure.product.ProductRateRequest;
import com.example.test_kte_labs.model.entity.ProductEntity;
import com.example.test_kte_labs.model.entity.ProductRatingEntity;
import com.example.test_kte_labs.model.repository.ProductRatingRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class ProductRatingService {
    private final ProductRatingRepository productRatingRepository;
    private final ProductService productService;
    private final ClientService clientService;

    public ProductRatingService(ProductRatingRepository productRatingRepository,
                                ProductService productService,
                                ClientService clientService) {
        this.productRatingRepository = productRatingRepository;
        this.productService = productService;
        this.clientService = clientService;
    }

    public List<ProductRatingEntity> getProductRatingList(String id) {
        return productRatingRepository.findByProductId(UUID.fromString(id));
    }

    public ProductExtraInfoResponse getProductExtraInfoResponse(String id) {
        ProductEntity product = productService.getProduct(id);
        ProductExtraInfoResponse response = new ProductExtraInfoResponse();
        response.setDescription(product.getDescription());
        response.setAverageRating(getAverageRating(id));
        response.setRatingMap(getRatingMap(id));
        return response;
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

    public void rateProduct(ProductRateRequest productRateRequest) {
        UUID clientId = clientService.getClient(productRateRequest.getClientId()).getId();
        UUID productId = productService.getProduct(productRateRequest.getProductId()).getId();

        Optional<ProductRatingEntity> productRatingOptional = productRatingRepository.findByClientIdAndProductId(clientId, productId);

        if (productRateRequest.getRating() == null) {
            if (productRatingOptional.isPresent()) {
                productRatingRepository.delete(productRatingOptional.get());
                return;
            } else {
                throw new ClientHasNotRatedThisProductException(clientId, productId);
            }
        }

        if (productRatingOptional.isPresent()) {
            throw new ClientHasAlreadyRatedThisProductException(clientId, productId);
        }

        ProductRatingEntity productRating = new ProductRatingEntity();
        productRating.setClientId(clientId);
        productRating.setProductId(productId);
        productRating.setRating(productRateRequest.getRating());
        productRatingRepository.save(productRating);
    }
}
