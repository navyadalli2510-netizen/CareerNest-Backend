package com.job.careerApp.Controllers;

import com.job.careerApp.Models.SavedJobs;
import com.job.careerApp.Services.SavedJobService;
import com.job.careerApp.dto.SavedJobresponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-jobs")
@CrossOrigin(origins = "http://localhost:5173")
public class SavedJobController {

    @Autowired
    private SavedJobService service;

    @GetMapping
    public List<SavedJobresponse> getJobs(Authentication authentication) {

        String email = authentication.getName();

        return service.allSavedJobs(email);
    }

    @PostMapping
    public SavedJobs addJob(@RequestBody SavedJobs savedJobs,
                            Authentication authentication) {

        String email = authentication.getName();

        return service.addJob(savedJobs, email);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Integer id) {

        service.deleteJobById(id);
    }
}