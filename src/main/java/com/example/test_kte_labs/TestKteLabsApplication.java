package com.example.test_kte_labs;

import com.example.test_kte_labs.model.service.ProductDiscountRateTask;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class TestKteLabsApplication {
    private final ProductDiscountRateTask productDiscountRateJob;

    public TestKteLabsApplication(ProductDiscountRateTask productDiscountRateJob) {
        this.productDiscountRateJob = productDiscountRateJob;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestKteLabsApplication.class, args);
    }

    @PostConstruct
    @Scheduled(fixedRate = 3600000) // one hour in milliseconds
    public void scheduleProductDiscountRateTask() {
        productDiscountRateJob.executeProductDiscountRateJob();
    }
}
