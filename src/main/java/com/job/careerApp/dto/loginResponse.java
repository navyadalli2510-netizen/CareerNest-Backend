package com.job.careerApp.dto;

public class loginResponse {

    private String token;
    private String role;

    public loginResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}