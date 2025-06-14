package com.qalbconnect.qalbconnect_backend.dto;

import java.time.LocalDate;

public class QazaCalculatorRequestDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private String gender; // "male" or "female"
    private Integer averagePeriodDays; // Nullable, only for female
    private Integer totalMonthlyCycles; // Nullable, only for female

    // Constructors
    public QazaCalculatorRequestDto() {
        this.endDate = LocalDate.now(); // Sensible default for a calculator
    }

    public QazaCalculatorRequestDto(LocalDate startDate, LocalDate endDate, String gender,
                                   Integer averagePeriodDays, Integer totalMonthlyCycles) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.gender = gender;
        this.averagePeriodDays = averagePeriodDays;
        this.totalMonthlyCycles = totalMonthlyCycles;
    }

    // Getters and Setters
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAveragePeriodDays() {
        return averagePeriodDays;
    }

    public void setAveragePeriodDays(Integer averagePeriodDays) {
        this.averagePeriodDays = averagePeriodDays;
    }

    public Integer getTotalMonthlyCycles() {
        return totalMonthlyCycles;
    }

    public void setTotalMonthlyCycles(Integer totalMonthlyCycles) {
        this.totalMonthlyCycles = totalMonthlyCycles;
    }
}