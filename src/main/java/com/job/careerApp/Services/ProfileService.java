package com.job.careerApp.Services;

import com.job.careerApp.Models.User;
import com.job.careerApp.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private UserRepo userRepo;

    public User getProfile(String email) {

        return userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));
    }

    public User updateProfile(String email, User updatedUser) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        System.out.println("Before Update");
        System.out.println(user.getName());

        user.setName(updatedUser.getName());

        // Recruiter company
        user.setCompany(updatedUser.getCompany());

        user.setPhone(updatedUser.getPhone());
        user.setLocation(updatedUser.getLocation());
        user.setCollege(updatedUser.getCollege());
        user.setDegree(updatedUser.getDegree());
        user.setSkills(updatedUser.getSkills());
        user.setPreferredRole(updatedUser.getPreferredRole());
        user.setExperience(updatedUser.getExperience());
        user.setResumeLink(updatedUser.getResumeLink());
        user.setGithub(updatedUser.getGithub());
        user.setLinkedin(updatedUser.getLinkedin());
        user.setAbout(updatedUser.getAbout());

        System.out.println("After Update");
        System.out.println(user.getName());

        User savedUser = userRepo.save(user);

        System.out.println("Saved Successfully");

        return savedUser;
    }
}