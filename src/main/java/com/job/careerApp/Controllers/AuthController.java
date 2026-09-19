package com.job.careerApp.Controllers;

import com.job.careerApp.Services.AuthService;
import com.job.careerApp.dto.LoginRequest;
import com.job.careerApp.dto.RegisterRequest;
import com.job.careerApp.dto.ResetPasswordRequest;
import com.job.careerApp.dto.loginResponse;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public loginResponse login(@RequestBody LoginRequest request) {
        System.out.println("===== LOGIN API CALLED =====");

        return authService.login(request);
    }

    @PutMapping("/reset-password")
    public String resetPassword(
            @RequestBody ResetPasswordRequest request) {

        authService.resetPassword(request);

        return "Password Updated Successfully";
    }

}
