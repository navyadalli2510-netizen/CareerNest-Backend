package com.job.careerApp.Controllers;

import com.job.careerApp.Models.Application;
import com.job.careerApp.Services.ApplicationService;
import com.job.careerApp.dto.ApplicationResponse;
import com.job.careerApp.dto.RecruiterApplicationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;


    // =====================================================
    // GET APPLICATIONS OF LOGGED-IN USER
    // =====================================================

    @GetMapping
    public List<ApplicationResponse> getApplications(
            Authentication authentication) {

        String email = authentication.getName();

        return applicationService.getApplicationsByUser(email);
    }


    // =====================================================
    // APPLY FOR JOB
    // =====================================================

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<?> applyForJob(
            @PathVariable int jobId,
            @RequestBody Application application,
            Authentication authentication) {

        try {

            String email = authentication.getName();

            Application saved =
                    applicationService.applyForJob(
                            jobId,
                            application,
                            email
                    );

            return ResponseEntity.ok(saved);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }


    // =====================================================
    // GET APPLICATIONS FOR ONE RECRUITER JOB
    // =====================================================

    @GetMapping("/recruiter/jobs/{jobId}")
    public ResponseEntity<?> getRecruiterJobApplications(
            @PathVariable int jobId,
            Authentication authentication) {

        try {

            String recruiterEmail =
                    authentication.getName();

            List<RecruiterApplicationResponse> applications =
                    applicationService
                            .getApplicationsForRecruiterJob(
                                    jobId,
                                    recruiterEmail
                            );

            return ResponseEntity.ok(applications);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(403)
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }


    // =====================================================
    // UPDATE APPLICATION STATUS
    // =====================================================

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<?> updateApplicationStatus(
            @PathVariable int applicationId,
            @RequestBody Map<String, String> request,
            Authentication authentication) {

        try {

            String status =
                    request.get("status");

            String recruiterEmail =
                    authentication.getName();

            RecruiterApplicationResponse response =
                    applicationService.updateStatus(
                            applicationId,
                            status,
                            recruiterEmail
                    );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(403)
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }


    // =====================================================
    // GET ALL APPLICATIONS FOR RECRUITER
    // =====================================================

    @GetMapping("/recruiter/all")
    public ResponseEntity<?> getAllRecruiterApplications(
            Authentication authentication) {

        try {

            String recruiterEmail =
                    authentication.getName();

            List<RecruiterApplicationResponse> applications =
                    applicationService
                            .getAllApplicationsForRecruiter(
                                    recruiterEmail
                            );

            return ResponseEntity.ok(applications);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(403)
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }
}