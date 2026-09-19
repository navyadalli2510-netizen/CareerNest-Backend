package com.job.careerApp.Services;

import com.job.careerApp.Models.Activity;
import com.job.careerApp.Models.Application;
import com.job.careerApp.Models.Jobs;
import com.job.careerApp.Models.User;

import com.job.careerApp.Repos.ActivityRepo;
import com.job.careerApp.Repos.AppRepo;
import com.job.careerApp.Repos.JobRepo;
import com.job.careerApp.Repos.UserRepo;

import com.job.careerApp.dto.ActivityResponse;
import com.job.careerApp.dto.RecruiterApplicationResponse;
import com.job.careerApp.dto.RecruiterDashboardResponse;
import com.job.careerApp.dto.RecruiterjobResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private AppRepo appRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ActivityRepo activityRepo;


    // =====================================================
    // RECRUITER DASHBOARD
    // =====================================================

    public RecruiterDashboardResponse getDashboard(String email) {

        // -------------------------------------------------
        // FIND LOGGED-IN RECRUITER
        // -------------------------------------------------

        User recruiter =
                userRepo.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter Not Found"
                                )
                        );


        // -------------------------------------------------
        // GET ONLY THIS RECRUITER'S JOBS
        // -------------------------------------------------

        List<Jobs> jobs =
                jobRepo.findByRecruiter(recruiter);


        // -------------------------------------------------
        // TOTAL JOBS
        // -------------------------------------------------

        long totalJobs = jobs.size();


        // -------------------------------------------------
        // TOTAL APPLICANTS
        // -------------------------------------------------

        long totalApplicants =
                appRepo.countByJob_Recruiter(recruiter);


        // -------------------------------------------------
        // ACCEPTED
        // -------------------------------------------------

        long accepted =
                appRepo.countByJob_RecruiterAndStatus(
                        recruiter,
                        "Accepted"
                );


        // -------------------------------------------------
        // REJECTED
        // -------------------------------------------------

        long rejected =
                appRepo.countByJob_RecruiterAndStatus(
                        recruiter,
                        "Rejected"
                );


        // =================================================
        // RECENT JOBS
        // =================================================

        List<RecruiterjobResponse> recentJobs =
                new ArrayList<>();


        for (Jobs job : jobs) {

            RecruiterjobResponse dto =
                    new RecruiterjobResponse();

            dto.setId(job.getId());

            dto.setTitle(
                    job.getTitle()
            );

            dto.setCompany(
                    job.getCompany()
            );

            dto.setLocation(
                    job.getLocation()
            );

            dto.setType(
                    job.getType()
            );

            dto.setApplicants(
                    appRepo.countByJob(job)
            );

            recentJobs.add(dto);
        }


        // =================================================
        // RECENT APPLICANTS
        // =================================================

        List<Application> applications =
                appRepo.findTop5ByJob_RecruiterOrderByIdDesc(
                        recruiter
                );


        List<RecruiterApplicationResponse>
                recentApplicants =
                new ArrayList<>();


        for (Application app : applications) {

            RecruiterApplicationResponse dto =
                    new RecruiterApplicationResponse();


            dto.setId(
                    app.getId()
            );


            if (app.getJob() != null) {

                dto.setJobId(
                        app.getJob().getId()
                );

                dto.setJobTitle(
                        app.getJob().getTitle()
                );

                dto.setCompany(
                        app.getJob().getCompany()
                );
            }


            dto.setName(
                    app.getName()
            );

            dto.setEmail(
                    app.getEmail()
            );

            dto.setPhone(
                    app.getPhone()
            );

            dto.setLocation(
                    app.getLocation()
            );

            dto.setQualification(
                    app.getQualification()
            );

            dto.setExperience(
                    app.getExperience()
            );

            dto.setResumeLink(
                    app.getResumeLink()
            );

            dto.setMessage(
                    app.getMessage()
            );

            dto.setStatus(
                    app.getStatus()
            );


            recentApplicants.add(dto);
        }


        // =================================================
        // RECENT ACTIVITIES
        // =================================================

        List<Activity> activityList =
                activityRepo
                        .findTop10ByRecruiterOrderByCreatedAtDesc(
                                recruiter
                        );


        List<ActivityResponse>
                activityResponse =
                new ArrayList<>();


        for (Activity activity : activityList) {

            ActivityResponse dto =
                    new ActivityResponse();


            dto.setId(
                    activity.getId()
            );

            dto.setAction(
                    activity.getAction()
            );

            dto.setMessage(
                    activity.getMessage()
            );

            dto.setCandidateName(
                    activity.getCandidateName()
            );

            dto.setJobTitle(
                    activity.getJobTitle()
            );

            dto.setStatus(
                    activity.getStatus()
            );

            dto.setCreatedAt(
                    activity.getCreatedAt()
            );


            activityResponse.add(dto);
        }


        // =================================================
        // FINAL RESPONSE
        // =================================================

        RecruiterDashboardResponse response =
                new RecruiterDashboardResponse();


        response.setTotalJobs(
                totalJobs
        );

        response.setTotalApplicants(
                totalApplicants
        );

        response.setAccepted(
                accepted
        );

        response.setRejected(
                rejected
        );

        response.setRecentJobs(
                recentJobs
        );

        response.setRecentApplicants(
                recentApplicants
        );

        response.setActivities(
                activityResponse
        );


        return response;
    }
}