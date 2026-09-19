package com.job.careerApp.dto;

import lombok.Data;

@Data
public class ApplicationResponse {

    private Integer id;
    private Integer jobId;
    private String title;
    private String company;
    private String location;
    private String status;

    private String name;
    private String email;
    private String phone;
}