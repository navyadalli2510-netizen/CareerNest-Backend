package com.job.careerApp.Services;

import com.job.careerApp.Models.Application;
import com.job.careerApp.Models.Jobs;
import com.job.careerApp.Models.User;
import com.job.careerApp.Repos.AppRepo;
import com.job.careerApp.Repos.JobRepo;
import com.job.careerApp.Repos.UserRepo;
import com.job.careerApp.dto.ApplicationResponse;
import com.job.careerApp.dto.RecruiterApplicationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private AppRepo appRepo;

    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private UserRepo userRepo;


    // =====================================================
    // GET APPLICATIONS FOR ONE RECRUITER JOB
    // =====================================================

    public List<RecruiterApplicationResponse>
    getApplicationsForRecruiterJob(
            int jobId,
            String recruiterEmail) {

        User recruiter =
                userRepo.findByEmail(recruiterEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter Not Found"
                                ));


        Jobs job =
                jobRepo.findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job Not Found"
                                ));


        // SECURITY CHECK

        if (job.getRecruiter() == null ||
                !job.getRecruiter()
                        .getId()
                        .equals(recruiter.getId())) {

            throw new RuntimeException(
                    "You are not authorized to view these applications"
            );
        }


        List<Application> applications =
                appRepo.findByJobOrderByIdDesc(job);


        List<RecruiterApplicationResponse> response =
                new ArrayList<>();


        for (Application application : applications) {

            RecruiterApplicationResponse dto =
                    new RecruiterApplicationResponse();


            // APPLICATION ID

            dto.setId(
                    application.getId()
            );


            // JOB DETAILS

            dto.setJobId(
                    job.getId()
            );

            dto.setJobTitle(
                    job.getTitle()
            );

            dto.setCompany(
                    job.getCompany()
            );


            // APPLICANT DETAILS

            dto.setName(
                    application.getName()
            );

            dto.setEmail(
                    application.getEmail()
            );

            dto.setPhone(
                    application.getPhone()
            );

            dto.setLocation(
                    application.getLocation()
            );

            dto.setQualification(
                    application.getQualification()
            );

            dto.setExperience(
                    application.getExperience()
            );


            // APPLICATION DETAILS

            dto.setResumeLink(
                    application.getResumeLink()
            );

            dto.setMessage(
                    application.getMessage()
            );

            dto.setStatus(
                    application.getStatus()
            );


            response.add(dto);
        }


        return response;
    }


    // =====================================================
    // ACCEPT / REJECT APPLICATION
    // =====================================================

    public RecruiterApplicationResponse updateStatus(
            int applicationId,
            String status,
            String recruiterEmail) {

        User recruiter =
                userRepo.findByEmail(recruiterEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter Not Found"
                                ));


        Application application =
                appRepo.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application Not Found"
                                ));


        Jobs job =
                application.getJob();


        if (job == null) {

            throw new RuntimeException(
                    "Job Not Found"
            );
        }


        // SECURITY CHECK

        if (job.getRecruiter() == null ||
                !job.getRecruiter()
                        .getId()
                        .equals(recruiter.getId())) {

            throw new RuntimeException(
                    "You are not authorized to update this application"
            );
        }


        // VALIDATE STATUS

        if (!"Accepted".equals(status) &&
                !"Rejected".equals(status)) {

            throw new RuntimeException(
                    "Invalid application status"
            );
        }


        // UPDATE STATUS

        application.setStatus(status);


        Application saved =
                appRepo.save(application);


        // CREATE RESPONSE

        RecruiterApplicationResponse dto =
                new RecruiterApplicationResponse();


        // APPLICATION ID

        dto.setId(
                saved.getId()
        );


        // JOB DETAILS

        dto.setJobId(
                job.getId()
        );

        dto.setJobTitle(
                job.getTitle()
        );

        dto.setCompany(
                job.getCompany()
        );


        // APPLICANT DETAILS

        dto.setName(
                saved.getName()
        );

        dto.setEmail(
                saved.getEmail()
        );

        dto.setPhone(
                saved.getPhone()
        );

        dto.setLocation(
                saved.getLocation()
        );

        dto.setQualification(
                saved.getQualification()
        );

        dto.setExperience(
                saved.getExperience()
        );


        // APPLICATION DETAILS

        dto.setResumeLink(
                saved.getResumeLink()
        );

        dto.setMessage(
                saved.getMessage()
        );

        dto.setStatus(
                saved.getStatus()
        );


        return dto;
    }


    // =====================================================
    // GET ALL APPLICANTS FOR LOGGED-IN RECRUITER
    // =====================================================

    public List<RecruiterApplicationResponse>
    getAllApplicationsForRecruiter(String recruiterEmail) {

        User recruiter =
                userRepo.findByEmail(recruiterEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Recruiter Not Found"
                                ));


        List<Application> applications =
                appRepo.findByJob_RecruiterOrderByIdDesc(
                        recruiter
                );


        List<RecruiterApplicationResponse> response =
                new ArrayList<>();


        for (Application application : applications) {

            RecruiterApplicationResponse dto =
                    new RecruiterApplicationResponse();


            // APPLICATION ID

            dto.setId(
                    application.getId()
            );


            // JOB DETAILS

            Jobs job =
                    application.getJob();

            if (job != null) {

                dto.setJobId(
                        job.getId()
                );

                dto.setJobTitle(
                        job.getTitle()
                );

                dto.setCompany(
                        job.getCompany()
                );
            }


            // APPLICATION DETAILS

            dto.setName(
                    application.getName()
            );

            dto.setEmail(
                    application.getEmail()
            );

            dto.setPhone(
                    application.getPhone()
            );

            dto.setLocation(
                    application.getLocation()
            );

            dto.setQualification(
                    application.getQualification()
            );

            dto.setExperience(
                    application.getExperience()
            );

            dto.setResumeLink(
                    application.getResumeLink()
            );

            dto.setMessage(
                    application.getMessage()
            );

            dto.setStatus(
                    application.getStatus()
            );


            response.add(dto);
        }


        return response;
    }


    // =====================================================
    // GET APPLICATIONS OF LOGGED-IN USER
    // =====================================================

    public List<ApplicationResponse>
    getApplicationsByUser(String email) {

        User user =
                userRepo.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User Not Found"
                                ));


        List<Application> applications =
                appRepo.findByUser(user);


        List<ApplicationResponse> response =
                new ArrayList<>();


        for (Application application : applications) {

            ApplicationResponse dto =
                    new ApplicationResponse();


            // APPLICATION ID

            dto.setId(
                    application.getId()
            );


            // JOB DETAILS

            Jobs job =
                    application.getJob();

            if (job != null) {

                dto.setJobId(
                        job.getId()
                );

                dto.setTitle(
                        job.getTitle()
                );

                dto.setCompany(
                        job.getCompany()
                );

                dto.setLocation(
                        job.getLocation()
                );
            }


            // APPLICATION DETAILS

            dto.setStatus(
                    application.getStatus()
            );

            dto.setName(
                    application.getName()
            );

            dto.setEmail(
                    application.getEmail()
            );

            dto.setPhone(
                    application.getPhone()
            );


            response.add(dto);
        }


        return response;
    }


    // =====================================================
    // APPLY FOR JOB
    // =====================================================

    public Application applyForJob(
            int jobId,
            Application application,
            String email) {

        // Find logged-in user

        User user =
                userRepo.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User Not Found"
                                )
                        );


        // Find job

        Jobs job =
                jobRepo.findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job Not Found"
                                )
                        );


        // Set logged-in user

        application.setUser(user);


        // Set selected job

        application.setJob(job);


        // Initial status

        application.setStatus("Applied");


        return appRepo.save(application);
    }
}