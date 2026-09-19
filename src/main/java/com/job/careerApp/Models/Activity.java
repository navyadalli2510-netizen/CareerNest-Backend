package com.job.careerApp.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private User recruiter;

    private String action;

    private String message;

    private String candidateName;

    private String jobTitle;

    private String status;

    private LocalDateTime createdAt;
}