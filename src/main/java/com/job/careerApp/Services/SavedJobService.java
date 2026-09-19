package com.job.careerApp.Services;

import com.job.careerApp.Models.Jobs;
import com.job.careerApp.Models.SavedJobs;
import com.job.careerApp.Models.User;
import com.job.careerApp.Repos.JobRepo;
import com.job.careerApp.Repos.SaveJobRepo;
import com.job.careerApp.Repos.UserRepo;
import com.job.careerApp.dto.SavedJobresponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SavedJobService {

    @Autowired
    private SaveJobRepo saverepo;

    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private UserRepo userRepo;

    // Save Job for Logged-in User
    public SavedJobs addJob(SavedJobs savedJobs, String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        savedJobs.setUser(user);

        return saverepo.save(savedJobs);
    }

    // Get Logged-in User Saved Jobs
    public List<SavedJobresponse> allSavedJobs(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        List<SavedJobs> savedJobs = saverepo.findByUser(user);

        List<SavedJobresponse> response = new ArrayList<>();

        for (SavedJobs savedJob : savedJobs) {

            Jobs job = jobRepo.findById(savedJob.getJobId()).orElse(null);

            SavedJobresponse dto = new SavedJobresponse();

            dto.setId(savedJob.getId());
            dto.setJobId(savedJob.getJobId());

            if (job != null) {
                dto.setJobTitle(job.getTitle());
                dto.setCompany(job.getCompany());
                dto.setLocation(job.getLocation());
                dto.setType(job.getType());
                dto.setSalary(job.getSalary());
                dto.setExperience(job.getExperience());
            }

            response.add(dto);
        }

        return response;
    }

    public SavedJobs saveJobsById(Integer id) {
        return saverepo.findById(id).orElse(null);
    }

    public void deleteJobById(Integer id) {
        saverepo.deleteById(id);
    }
}