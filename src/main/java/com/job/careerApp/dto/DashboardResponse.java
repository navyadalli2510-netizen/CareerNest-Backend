package com.job.careerApp.dto;

import com.job.careerApp.Models.Application;
import com.job.careerApp.Models.Jobs;
import lombok.Data;

import java.util.List;

@Data
public class DashboardResponse {

    private long totalJobs;
    private long totalApplicants;
    private long accepted;
    private long rejected;

    private List<Jobs> recentJobs;
    private List<Application> recentApplicants;
    private List<ActivityResponse> activities;
}