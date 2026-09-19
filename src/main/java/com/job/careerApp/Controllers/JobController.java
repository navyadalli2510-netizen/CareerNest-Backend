package com.job.careerApp.Controllers;

import com.job.careerApp.Models.Jobs;
import com.job.careerApp.Services.JobService;
import com.job.careerApp.dto.RecruiterjobResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:5173")
public class JobController {

    @Autowired
    private JobService service;

    @GetMapping
    public List<Jobs> getAllJobs() {
        return service.getAllJobs();
    }

    @PostMapping
    public Jobs addJob(
            @RequestBody Jobs job,
            Authentication authentication) {

        String email = authentication.getName();

        return service.addJobs(job, email);
    }

    @GetMapping("/recruiter")
    public List<RecruiterjobResponse> getRecruiterJobs(
            Authentication authentication) {

        String email = authentication.getName();

        return service.getRecruiterJobs(email);
    }

    // =========================================================
    // RECOMMENDED JOBS BASED ON USER SKILLS
    // =========================================================

    @GetMapping("/recommended")
    public List<Jobs> getRecommendedJobs(
            Authentication authentication) {

        String email = authentication.getName();

        return service.getRecommendedJobs(email);
    }

    @GetMapping("/{id}")
    public Jobs getJobById(
            @PathVariable int id) {

        return service.getJobsById(id);
    }

    @GetMapping("/recruiter/{id}")
    public Jobs getRecruiterJobById(
            @PathVariable int id,
            Authentication authentication) {

        String email = authentication.getName();

        return service.getRecruiterJobById(id, email);
    }

    @PutMapping("/{id}")
    public Jobs updateJob(
            @PathVariable int id,
            @RequestBody Jobs job,
            Authentication authentication) {

        String email = authentication.getName();

        return service.updateJobs(id, job, email);
    }

    @DeleteMapping("/{id}")
    public Jobs deleteJob(
            @PathVariable int id,
            Authentication authentication) {

        String email = authentication.getName();

        return service.deleteJob(id, email);
    }

    @GetMapping("/search")
    public List<Jobs> searchJobs(
            @RequestParam String keyword) {

        return service.searchJobs(keyword);
    }
}