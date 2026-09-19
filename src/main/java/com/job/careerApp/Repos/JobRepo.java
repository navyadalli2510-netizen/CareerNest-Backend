package com.job.careerApp.Repos;

import com.job.careerApp.Models.Jobs;
import com.job.careerApp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepo extends JpaRepository<Jobs, Integer> {

    // Only jobs belonging to particular recruiter
    List<Jobs> findByRecruiter(User recruiter);

    // Search all jobs
    @Query("""
           SELECT j FROM Jobs j
           WHERE LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(j.location) LIKE LOWER(CONCAT('%', :keyword, '%'))
           """)
    List<Jobs> searchJobs(@Param("keyword") String keyword);
}