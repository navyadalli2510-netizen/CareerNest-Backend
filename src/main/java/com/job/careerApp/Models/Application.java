package com.job.careerApp.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Jobs job;

    private String name;
    private String email;
    private String phone;
    private String location;
    private String qualification;
    private String experience;
    private String resumeLink;
    private String message;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}