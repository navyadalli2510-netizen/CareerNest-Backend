package com.job.careerApp.Services;

import com.job.careerApp.Models.Activity;
import com.job.careerApp.Models.Jobs;
import com.job.careerApp.Models.User;
import com.job.careerApp.Repos.ActivityRepo;
import com.job.careerApp.Repos.AppRepo;
import com.job.careerApp.Repos.JobRepo;
import com.job.careerApp.Repos.UserRepo;
import com.job.careerApp.dto.RecruiterjobResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepo repo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AppRepo appRepo;

    @Autowired
    private ActivityRepo activityRepo;


    // =========================================================
    // GET ALL JOBS
    // =========================================================

    public List<Jobs> getAllJobs() {

        return repo.findAll();
    }


    // =========================================================
    // RECRUITER - ADD JOB
    // =========================================================

    public Jobs addJobs(
            Jobs job,
            String email) {

        User recruiter = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter Not Found")
                );

        // VERY IMPORTANT
        // Attach logged-in recruiter to job
        job.setRecruiter(recruiter);

        // Don't manually maintain applicant count
        job.setApplicants(0);

        Jobs savedJob = repo.save(job);


        // =====================================================
        // ACTIVITY
        // =====================================================

        Activity activity = new Activity();

        activity.setRecruiter(recruiter);
        activity.setAction("JOB_POSTED");

        activity.setMessage(
                "You posted a new job: " + savedJob.getTitle()
        );

        activity.setJobTitle(
                savedJob.getTitle()
        );

        activity.setStatus("Active");

        activity.setCreatedAt(
                LocalDateTime.now()
        );

        activityRepo.save(activity);


        return savedJob;
    }


    // =========================================================
    // GET ONLY LOGGED-IN RECRUITER JOBS
    // =========================================================

    public List<RecruiterjobResponse> getRecruiterJobs(
            String email) {

        User recruiter = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter Not Found")
                );

        List<Jobs> jobs =
                repo.findByRecruiter(recruiter);

        List<RecruiterjobResponse> response =
                new ArrayList<>();

        for (Jobs job : jobs) {

            RecruiterjobResponse dto =
                    new RecruiterjobResponse();

            dto.setId(job.getId());
            dto.setTitle(job.getTitle());
            dto.setCompany(job.getCompany());
            dto.setLocation(job.getLocation());
            dto.setType(job.getType());

            // REAL applicant count from Application table
            dto.setApplicants(
                    appRepo.countByJob(job)
            );

            response.add(dto);
        }

        return response;
    }


    // =========================================================
    // GET JOB BY ID
    // =========================================================

    public Jobs getJobsById(int id) {

        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job Not Found")
                );
    }


    // =========================================================
    // GET PARTICULAR JOB FOR LOGGED-IN RECRUITER
    // =========================================================

    public Jobs getRecruiterJobById(
            int id,
            String email) {

        User recruiter = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter Not Found")
                );

        Jobs job = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job Not Found")
                );

        // Security
        if (job.getRecruiter() == null ||
                !job.getRecruiter()
                        .getId()
                        .equals(recruiter.getId())) {

            throw new RuntimeException(
                    "You are not authorized to access this job"
            );
        }

        return job;
    }


    // =========================================================
    // UPDATE JOB
    // =========================================================

    public Jobs updateJobs(
            int id,
            Jobs job,
            String email) {

        User recruiter = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter Not Found")
                );

        Jobs existingJob = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job Not Found")
                );


        // =====================================================
        // SECURITY CHECK
        // =====================================================

        if (existingJob.getRecruiter() == null ||
                !existingJob.getRecruiter()
                        .getId()
                        .equals(recruiter.getId())) {

            throw new RuntimeException(
                    "You are not authorized to update this job"
            );
        }


        // =====================================================
        // UPDATE ONLY EDITABLE FIELDS
        // =====================================================

        existingJob.setTitle(job.getTitle());
        existingJob.setCompany(job.getCompany());
        existingJob.setLocation(job.getLocation());
        existingJob.setType(job.getType());
        existingJob.setDescription(job.getDescription());
        existingJob.setRequirements(job.getRequirements());
        existingJob.setResponsibilities(job.getResponsibilities());


        // Keep original recruiter
        existingJob.setRecruiter(
                recruiter
        );


        Jobs updatedJob =
                repo.save(existingJob);


        // =====================================================
        // ACTIVITY
        // =====================================================

        Activity activity = new Activity();

        activity.setRecruiter(recruiter);

        activity.setAction(
                "JOB_UPDATED"
        );

        activity.setMessage(
                "You updated the job: "
                        + updatedJob.getTitle()
        );

        activity.setJobTitle(
                updatedJob.getTitle()
        );

        activity.setStatus("Updated");

        activity.setCreatedAt(
                LocalDateTime.now()
        );

        activityRepo.save(activity);


        return updatedJob;
    }


    // =========================================================
    // DELETE JOB
    // =========================================================

    public Jobs deleteJob(
            int id,
            String email) {

        User recruiter = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter Not Found")
                );

        Jobs job = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job Not Found")
                );


        // =====================================================
        // SECURITY
        // =====================================================

        if (job.getRecruiter() == null ||
                !job.getRecruiter()
                        .getId()
                        .equals(recruiter.getId())) {

            throw new RuntimeException(
                    "You are not authorized to delete this job"
            );
        }


        String jobTitle = job.getTitle();


        // =====================================================
        // ACTIVITY BEFORE DELETE
        // =====================================================

        Activity activity = new Activity();

        activity.setRecruiter(recruiter);

        activity.setAction(
                "JOB_DELETED"
        );

        activity.setMessage(
                "You deleted the job: " + jobTitle
        );

        activity.setJobTitle(
                jobTitle
        );

        activity.setStatus(
                "Deleted"
        );

        activity.setCreatedAt(
                LocalDateTime.now()
        );

        activityRepo.save(activity);


        // =====================================================
        // DELETE
        // =====================================================

        repo.delete(job);


        return job;
    }


    // =========================================================
    // SEARCH JOBS
    // =========================================================

    public List<Jobs> searchJobs(
            String keyword) {

        return repo.searchJobs(keyword);
    }

    // =========================================================
// RECOMMENDED JOBS FOR USER
// =========================================================

    public List<Jobs> getRecommendedJobs(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found")
                );

        List<Jobs> allJobs = repo.findAll();

        String userSkills = user.getSkills();

        if (userSkills == null || userSkills.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String[] skills =
                userSkills.toLowerCase()
                        .split("[,;]");

        List<Jobs> recommended = new ArrayList<>();

        for (Jobs job : allJobs) {

            String jobText =
                    (
                            (job.getTitle() == null ? "" : job.getTitle()) + " " +
                                    (job.getDescription() == null ? "" : job.getDescription()) + " " +
                                    (job.getRequirements() == null ? "" : job.getRequirements())
                    ).toLowerCase();

            boolean matched = false;

            for (String skill : skills) {

                String cleanSkill =
                        skill.trim().toLowerCase();

                if (!cleanSkill.isEmpty()
                        && jobText.contains(cleanSkill)) {

                    matched = true;
                    break;
                }
            }

            if (matched) {
                recommended.add(job);
            }
        }

        return recommended;
    }
}