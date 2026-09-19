package com.job.careerApp.Repos;

import com.job.careerApp.Models.Application;
import com.job.careerApp.Models.Jobs;
import com.job.careerApp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepo extends JpaRepository<Application, Integer> {

 // Applications of one user
 List<Application> findByUser(User user);

 // Applications for one particular job
 List<Application> findByJob(Jobs job);

 // Latest applications for one job
 List<Application> findByJobOrderByIdDesc(Jobs job);

 // Count applications for one job
 long countByJob(Jobs job);

 // Total applications received by recruiter
 long countByJob_Recruiter(User recruiter);

 // Accepted applications
 long countByJob_RecruiterAndStatus(
         User recruiter,
         String status
 );

 // Latest applications for recruiter
 List<Application> findTop5ByJob_RecruiterOrderByIdDesc(
         User recruiter
 );

 // All applications received by one recruiter
 List<Application> findByJob_RecruiterOrderByIdDesc(
         User recruiter
 );
}