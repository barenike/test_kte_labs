package com.example.test_kte_labs.controller.rest;

import com.example.test_kte_labs.infrastructure.stats.StatsRequest;
import com.example.test_kte_labs.infrastructure.stats.StatsResponse;
import com.example.test_kte_labs.model.service.StatsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {
    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats(@ModelAttribute @Valid StatsRequest statsRequest) {
        StatsResponse statsResponse = statsService.getStats(statsRequest);
        return new ResponseEntity<>(statsResponse, HttpStatus.OK);
    }
}
