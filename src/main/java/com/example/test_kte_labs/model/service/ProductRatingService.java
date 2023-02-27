package com.example.test_kte_labs.model.service;

import com.example.test_kte_labs.exceptions.ClientDidNotBuyProductException;
import com.example.test_kte_labs.exceptions.ClientHasAlreadyRatedThisProductException;
import com.example.test_kte_labs.exceptions.ClientHasNotRatedThisProductException;
import com.example.test_kte_labs.infrastructure.product.ProductExtraInfoResponse;
import com.example.test_kte_labs.infrastructure.product.ProductRateRequest;
import com.example.test_kte_labs.model.entity.OrderDetailEntity;
import com.example.test_kte_labs.model.entity.OrderEntity;
import com.example.test_kte_labs.model.entity.ProductEntity;
import com.example.test_kte_labs.model.entity.ProductRatingEntity;
import com.example.test_kte_labs.model.repository.ProductRatingRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class ProductRatingService {
    private final ProductRatingRepository productRatingRepository;
    private final ClientService clientService;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    public ProductRatingService(
            ProductRatingRepository productRatingRepository,
            ClientService clientService,
            ProductService productService,
            OrderService orderService,
            OrderDetailService orderDetailService) {
        this.productRatingRepository = productRatingRepository;
        this.clientService = clientService;
        this.productService = productService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
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

        checkWhetherClientDidBuyProduct(clientId, productId);

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

    private void checkWhetherClientDidBuyProduct(UUID clientId, UUID productId) {
        List<OrderEntity> orders = orderService.findByClientId(String.valueOf(clientId));

        boolean didClientBuyProduct = false;
        for (OrderEntity order : orders) {
            List<OrderDetailEntity> orderDetails = orderDetailService.getOrderDetailListByOrderId(order.getId());
            for (OrderDetailEntity orderDetail : orderDetails) {
                if (orderDetail.getProductId() == productId) {
                    didClientBuyProduct = true;
                    break;
                }
            }
        }

        if (!didClientBuyProduct) {
            throw new ClientDidNotBuyProductException(clientId, productId);
        }
    }
}
