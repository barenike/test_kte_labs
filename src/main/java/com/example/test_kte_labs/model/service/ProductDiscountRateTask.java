package com.example.test_kte_labs.model.service;

import com.example.test_kte_labs.model.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ProductDiscountRateTask {
    private final ProductService productService;

    public ProductDiscountRateTask(ProductService productService) {
        this.productService = productService;
    }

    public void executeProductDiscountRateJob() {
        List<ProductEntity> productsWithNotNullDiscountRate = productService.getProductListWithNonZeroDiscountRate();
        productsWithNotNullDiscountRate.forEach(productService::deleteDiscountRate);

        List<ProductEntity> products = productService.getProducts();

        Random random = new Random();
        int productNumber = random.nextInt(products.size() - 1);
        int discountRate = random.nextInt(5, 11);

        productService.setDiscountRate(products.get(productNumber), discountRate);
    }
}
