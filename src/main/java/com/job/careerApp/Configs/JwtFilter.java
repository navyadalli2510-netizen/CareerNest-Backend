package com.job.careerApp.Configs;

import com.job.careerApp.Services.CustomUserDetailService;
import com.job.careerApp.Services.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final CustomUserDetailService customUserDetailService;


    public JwtFilter(
            JWTService jwtService,
            CustomUserDetailService customUserDetailService) {

        this.jwtService = jwtService;
        this.customUserDetailService =
                customUserDetailService;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {


        System.out.println("\n========================================");
        System.out.println(
                "Request URI : " +
                        request.getRequestURI()
        );


        String authHeader =
                request.getHeader("Authorization");


        System.out.println(
                "Authorization Header : " +
                        authHeader
        );


        // =====================================================
        // NO TOKEN
        // =====================================================

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            System.out.println(
                    "No JWT Token Found"
            );

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }


        // =====================================================
        // EXTRACT TOKEN
        // =====================================================

        String jwt =
                authHeader.substring(7);


        System.out.println(
                "JWT Token : " + jwt
        );


        try {

            // =================================================
            // EXTRACT EMAIL
            // =================================================

            String email =
                    jwtService.extractUsername(jwt);


            System.out.println(
                    "Extracted Email : " + email
            );


            // =================================================
            // AUTHENTICATE
            // =================================================

            if (email != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {


                UserDetails userDetails =
                        customUserDetailService
                                .loadUserByUsername(email);


                System.out.println(
                        "User Loaded : " +
                                userDetails.getUsername()
                );


                // =============================================
                // VALIDATE TOKEN
                // =============================================

                if (jwtService.isTokenValid(
                        jwt,
                        userDetails)) {


                    System.out.println(
                            "JWT VALID"
                    );


                    UsernamePasswordAuthenticationToken
                            authentication =

                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );


                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );


                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authentication
                            );


                    System.out.println(
                            "AUTHENTICATION SET"
                    );


                    System.out.println(
                            "AUTHORITIES : " +
                                    userDetails.getAuthorities()
                    );

                } else {

                    System.out.println(
                            "JWT INVALID"
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "JWT ERROR : " +
                            e.getMessage()
            );

        }


        filterChain.doFilter(
                request,
                response
        );
    }
}