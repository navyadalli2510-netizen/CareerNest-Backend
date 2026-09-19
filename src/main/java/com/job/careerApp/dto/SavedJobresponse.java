package com.job.careerApp.dto;

import lombok.Data;

@Data
public class SavedJobresponse {

    private Integer id;
    private Integer jobId;

    private String jobTitle;
    private String company;
    private String location;

    private String type;
    private String salary;
    private String experience;
}