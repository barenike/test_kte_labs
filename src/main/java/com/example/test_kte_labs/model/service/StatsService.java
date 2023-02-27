package com.example.test_kte_labs.model.service;

import com.example.test_kte_labs.exceptions.TwoParametersInRequestException;
import com.example.test_kte_labs.infrastructure.stats.StatsRequest;
import com.example.test_kte_labs.infrastructure.stats.StatsResponse;
import com.example.test_kte_labs.model.entity.OrderDetailEntity;
import com.example.test_kte_labs.model.entity.OrderEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StatsService {
    private final ClientService clientService;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    public StatsService(ClientService clientService,
                        ProductService productService,
                        OrderService orderService,
                        OrderDetailService orderDetailService) {
        this.clientService = clientService;
        this.productService = productService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    public StatsResponse getStats(StatsRequest statsRequest) {
        String clientId = statsRequest.getClientId();
        String productId = statsRequest.getProductId();
        StatsResponse statsResponse;

        if (clientId != null && productId != null) {
            throw new TwoParametersInRequestException(clientId, productId);
        } else if (productId != null) {
            statsResponse = getProductStatsResponse(productId);
        } else {
            statsResponse = getClientStatsResponse(clientId);
        }
        return statsResponse;
    }

    private StatsResponse getProductStatsResponse(String id) {
        // Check whether product exists
        productService.getProduct(String.valueOf(id));

        StatsResponse statsResponse = new StatsResponse();
        double subtotalProductPricesSum = 0.0;
        double productDiscountsSum = 0.0;

        List<OrderDetailEntity> orderDetails = orderDetailService.getOrderDetailListByProductId(UUID.fromString(id));
        for (OrderDetailEntity orderDetail : orderDetails) {
            Double subtotalPrice = orderDetail.getSubtotalPrice();
            subtotalProductPricesSum += subtotalPrice;
            productDiscountsSum += subtotalPrice - orderDetail.getTotalPrice();
        }

        statsResponse.setChequeCount(orderDetailService.getDistinctOrderIdListByProductId(UUID.fromString(id)).size());
        statsResponse.setSubtotalPriceSum(subtotalProductPricesSum);
        statsResponse.setDiscountSum(productService.roundToTwoDecimalPlaces(productDiscountsSum));

        return statsResponse;
    }

    private StatsResponse getClientStatsResponse(String id) {
        // Check whether client exists
        clientService.getClient(String.valueOf(id));

        StatsResponse statsResponse = new StatsResponse();
        double subtotalProductPricesSum = 0.0;
        double productDiscountsSum = 0.0;

        List<OrderEntity> orders = orderService.findByClientId(String.valueOf(UUID.fromString(id)));
        for (OrderEntity order : orders) {
            List<OrderDetailEntity> orderDetails = orderDetailService.getOrderDetailListByOrderId(order.getId());
            for (OrderDetailEntity orderDetail : orderDetails) {
                Double subtotalPrice = orderDetail.getSubtotalPrice();
                subtotalProductPricesSum += subtotalPrice;
                productDiscountsSum += subtotalPrice - orderDetail.getTotalPrice();
            }
        }

        statsResponse.setChequeCount(orders.size());
        statsResponse.setSubtotalPriceSum(subtotalProductPricesSum);
        statsResponse.setDiscountSum(productService.roundToTwoDecimalPlaces(productDiscountsSum));

        return statsResponse;
    }
}
