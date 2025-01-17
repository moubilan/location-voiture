package com.location.voiture.controllers;

import com.location.voiture.dto.DashboardMetricsDto;
import com.location.voiture.services.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public DashboardMetricsDto getDashboardMetrics() {
        return dashboardService.getDashboardMetrics();
    }
}