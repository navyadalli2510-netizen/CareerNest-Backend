package com.job.careerApp.Controllers;

import com.job.careerApp.Models.User;
import com.job.careerApp.Services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public User getProfile(Authentication authentication) {

        String email = authentication.getName();

        return profileService.getProfile(email);
    }

    @PutMapping
    public User updateProfile(Authentication authentication,
                              @RequestBody User user) {

        String email = authentication.getName();

        return profileService.updateProfile(email, user);
    }
}