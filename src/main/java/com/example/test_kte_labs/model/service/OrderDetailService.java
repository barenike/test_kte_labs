package com.example.test_kte_labs.model.service;

import com.example.test_kte_labs.infrastructure.order_detail.OrderCreationDetail;
import com.example.test_kte_labs.model.entity.OrderDetailEntity;
import com.example.test_kte_labs.model.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;

    public OrderDetailService(OrderDetailRepository orderDetailRepository, ProductService productService) {
        this.orderDetailRepository = orderDetailRepository;
        this.productService = productService;
    }

    public void create(OrderCreationDetail orderCreationDetail,
                       Double productFinalPrice,
                       UUID orderId,
                       String clientId) {
        OrderDetailEntity orderDetail = new OrderDetailEntity();
        Integer quantity = orderCreationDetail.getQuantity();
        String productId = orderCreationDetail.getProductId();
        Double productPrice = productService.getProduct(productId).getPrice();
        Double subtotalPrice = productPrice * quantity;

        orderDetail.setOrderId(orderId);
        orderDetail.setProductId(UUID.fromString(productId));
        orderDetail.setQuantity(quantity);
        orderDetail.setSubtotalPrice(subtotalPrice);
        orderDetail.setTotalPrice(productFinalPrice);
        orderDetail.setTotalDiscountRate(productService.getTotalDiscountRate(clientId, productId, quantity));

        orderDetailRepository.save(orderDetail);
    }

    public List<OrderDetailEntity> getOrderDetailListByProductId(UUID id) {
        return orderDetailRepository.findByProductId(id);
    }

    public List<OrderDetailEntity> getOrderDetailListByOrderId(UUID id) {
        return orderDetailRepository.findByOrderId(id);
    }

    public List<UUID> getDistinctOrderIdListByProductId(UUID id) {
        return orderDetailRepository.getDistinctOrderIdListByProductId(id);
    }
}
