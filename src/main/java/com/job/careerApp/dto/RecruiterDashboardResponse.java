package com.job.careerApp.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecruiterDashboardResponse {

    private long totalJobs;

    private long totalApplicants;

    private long accepted;

    private long rejected;

    private List<RecruiterjobResponse> recentJobs;

    private List<RecruiterApplicationResponse> recentApplicants;

    private List<ActivityResponse> activities;
}