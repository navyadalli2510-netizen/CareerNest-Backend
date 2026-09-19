package com.job.careerApp.Controllers;

import com.job.careerApp.Services.DashboardService;
import com.job.careerApp.dto.DashboardResponse;

import com.job.careerApp.dto.RecruiterDashboardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    @Autowired
    private DashboardService service;


    @GetMapping("/recruiter")
    public RecruiterDashboardResponse getRecruiterDashboard(
            Authentication authentication) {

        String email = authentication.getName();

        return service.getDashboard(email);
    }
}