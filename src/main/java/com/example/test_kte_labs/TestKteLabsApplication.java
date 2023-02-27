package com.example.test_kte_labs;

import com.example.test_kte_labs.model.service.task.ProductDiscountRateService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class TestKteLabsApplication {
    private final ProductDiscountRateService productDiscountRateService;

    public TestKteLabsApplication(ProductDiscountRateService productDiscountRateService) {
        this.productDiscountRateService = productDiscountRateService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestKteLabsApplication.class, args);
    }

    @PostConstruct
    @Scheduled(fixedRate = 3600000) // one hour in milliseconds
    public void scheduleProductDiscountRateTask() {
        productDiscountRateService.executeProductDiscountRateTask();
    }
}
