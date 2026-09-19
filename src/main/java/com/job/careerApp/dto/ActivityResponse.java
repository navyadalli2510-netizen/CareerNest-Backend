package com.job.careerApp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityResponse {

    private Integer id;
    private String action;
    private String message;
    private String candidateName;
    private String jobTitle;
    private String status;
    private LocalDateTime createdAt;
}