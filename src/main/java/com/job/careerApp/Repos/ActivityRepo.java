package com.job.careerApp.Repos;

import com.job.careerApp.Models.Activity;
import com.job.careerApp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Integer> {

    List<Activity> findTop10ByRecruiterOrderByCreatedAtDesc(
            User recruiter
    );
}