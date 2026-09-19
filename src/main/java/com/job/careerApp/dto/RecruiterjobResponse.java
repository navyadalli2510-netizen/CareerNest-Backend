package com.job.careerApp.dto;


    public class RecruiterjobResponse {

        private Integer id;
        private String title;
        private String company;
        private String location;
        private String type;

        private long applicants;

        // Getters & Setters

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getApplicants() {
            return applicants;
        }

        public void setApplicants(long applicants) {
            this.applicants = applicants;
        }
    }




