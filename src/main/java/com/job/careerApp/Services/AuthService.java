package com.job.careerApp.Services;

import com.job.careerApp.Models.User;
import com.job.careerApp.Repos.UserRepo;
import com.job.careerApp.dto.LoginRequest;
import com.job.careerApp.dto.RegisterRequest;
import com.job.careerApp.dto.ResetPasswordRequest;
import com.job.careerApp.dto.loginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JWTService jwtService) {

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {

        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepo.save(user);

        return "User Registered Successfully";
    }

    public loginResponse login(LoginRequest request) {

        try {
            System.out.println("Before Authentication");

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            System.out.println("Authentication Success");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new loginResponse(
                jwtService.generateToken(user),
                user.getRole().name()
        );
    }

    public void resetPassword(ResetPasswordRequest request) {

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email Not Found"));

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(encodedPassword);

        userRepo.save(user);
    }
}