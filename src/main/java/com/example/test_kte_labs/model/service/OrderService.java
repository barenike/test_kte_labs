package com.example.test_kte_labs.model.service;

import com.example.test_kte_labs.exceptions.ReceivedTotalDoesNotMatchCalculatedTotalException;
import com.example.test_kte_labs.infrastructure.order.OrderCreationRequest;
import com.example.test_kte_labs.infrastructure.order_detail.OrderCreationDetail;
import com.example.test_kte_labs.infrastructure.product.ProductFinalPriceRequest;
import com.example.test_kte_labs.model.entity.OrderEntity;
import com.example.test_kte_labs.model.repository.OrderRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderDetailService orderDetailService;

    public OrderService(OrderRepository orderRepository,
                        ProductService productService,
                        OrderDetailService orderDetailService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderDetailService = orderDetailService;
    }

    public List<OrderEntity> findByClientId(String id) {
        return orderRepository.findByClientId(UUID.fromString(id));
    }

    @Transactional
    public String createAndGetCheque(OrderCreationRequest orderCreationRequest) {
        OrderEntity order = new OrderEntity();
        String clientId = orderCreationRequest.getClientId();
        Double calculatedTotalPrice = 0.0;

        order.setClientId(UUID.fromString(clientId));
        LocalDateTime currentMoscowDate = getCurrentMoscowDate();
        order.setOrderDate(currentMoscowDate);

        Optional<OrderEntity> latestOrder = orderRepository.findFirstByOrderByOrderDateDesc();

        String cheque;
        if (latestOrder.isPresent() && latestOrder.get().getOrderDate().toLocalDate().equals(currentMoscowDate.toLocalDate())) {
            cheque = String.valueOf(Integer.parseInt(latestOrder.get().getCheque()) + 1);
            cheque = StringUtils.leftPad(cheque, 5, "0");
            order.setCheque(cheque);
        } else {
            cheque = "00100";
            order.setCheque(cheque);
        }

        orderRepository.save(order);

        for (OrderCreationDetail orderCreationDetail : orderCreationRequest.getOrderCreationDetails()) {
            ProductFinalPriceRequest productFinalPriceRequest = new ProductFinalPriceRequest();

            productFinalPriceRequest.setClientId(clientId);
            productFinalPriceRequest.setProductId(orderCreationDetail.getProductId());
            productFinalPriceRequest.setQuantity(orderCreationDetail.getQuantity());

            Double productFinalPrice = productService.getProductFinalPrice(productFinalPriceRequest);
            calculatedTotalPrice += productFinalPrice;

            orderDetailService.create(orderCreationDetail, productFinalPrice, order.getId(), clientId);
        }

        Double receivedTotalPrice = orderCreationRequest.getTotalPrice();
        if (!calculatedTotalPrice.equals(receivedTotalPrice)) {
            throw new ReceivedTotalDoesNotMatchCalculatedTotalException(calculatedTotalPrice, receivedTotalPrice);
        }

        return cheque;
    }

    public LocalDateTime getCurrentMoscowDate() {
        ZoneId zone = ZoneId.of("Europe/Moscow");
        ZonedDateTime date = ZonedDateTime.now(zone);
        return date.toLocalDateTime();
    }
}
