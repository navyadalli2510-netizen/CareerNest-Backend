package com.job.careerApp.dto;

import lombok.Data;

@Data
public class RecruiterApplicationResponse {

    private int id;

    // Job details
    private int jobId;
    private String jobTitle;
    private String company;

    // Applicant details
    private String name;
    private String email;
    private String phone;
    private String location;
    private String qualification;
    private String experience;

    // Application details
    private String resumeLink;
    private String message;
    private String status;
}