package com.yourapp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
    private LocalDateTime submissionTime;
    private String nameCompany;
    private double weight;

    public Order(LocalDateTime submissionTime, String nameCompany, double weight) {
        this.submissionTime = submissionTime;
        this.nameCompany = nameCompany;
        this.weight = weight;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public double getWeight() {
        return weight;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    @Override
    public String toString() {
        return "Order{" +
                "submissionTime=" + submissionTime +
                ", nameCompany='" + nameCompany + '\'' +
                ", weight=" + weight +
                '}';
    }

}
