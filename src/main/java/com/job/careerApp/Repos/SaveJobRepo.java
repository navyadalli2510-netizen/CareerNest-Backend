package com.job.careerApp.Repos;

import com.job.careerApp.Models.SavedJobs;
import com.job.careerApp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaveJobRepo extends JpaRepository<SavedJobs,Integer> {

    List<SavedJobs> findByUser(User user);

}