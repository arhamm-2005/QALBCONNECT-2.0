package com.qalbconnect.qalbconnect_backend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "qaza_prayer_entries")
public class QazaPrayerEntry {

    @Id
    private String id; // MongoDB uses String IDs by default

    @Field("username") // Map to a field named "username" in MongoDB document
    private String username;

    private LocalDate startDate;
    private LocalDate endDate;
    private long totalCalendarDays;
    private String gender;
    private Integer averagePeriodDays;
    private Integer totalMonthlyCycles;
    private long excludedPeriodDays;
    private long finalMissedDaysForCalculation;

    private long fajrCount;
    private long zuhrCount;
    private long asrCount;
    private long maghribCount;
    private long ishaCount;
    private long witrCount;
    private LocalDateTime calculationTimestamp;

    // NEW FIELD: Sum of all individual Qaza prayer counts (Fajr+Zuhr+Asr+Maghrib+Isha+Witr)
    private long totalRemainingPrayers; // Added this field

    // Constructors
    public QazaPrayerEntry() {
        this.calculationTimestamp = LocalDateTime.now();
    }

    // Constructor without ID (for new entries)
    public QazaPrayerEntry(String username, LocalDate startDate, LocalDate endDate,
                           long totalCalendarDays, String gender, Integer averagePeriodDays,
                           Integer totalMonthlyCycles, long excludedPeriodDays,
                           long finalMissedDaysForCalculation, long fajrCount, long zuhrCount,
                           long asrCount, long maghribCount, long ishaCount, long witrCount,
                           long totalRemainingPrayers) { // Added totalRemainingPrayers to constructor
        this.username = username;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCalendarDays = totalCalendarDays;
        this.gender = gender;
        this.averagePeriodDays = averagePeriodDays;
        this.totalMonthlyCycles = totalMonthlyCycles;
        this.excludedPeriodDays = excludedPeriodDays;
        this.finalMissedDaysForCalculation = finalMissedDaysForCalculation;
        this.fajrCount = fajrCount;
        this.zuhrCount = zuhrCount;
        this.asrCount = asrCount;
        this.maghribCount = maghribCount;
        this.ishaCount = ishaCount;
        this.witrCount = witrCount;
        this.calculationTimestamp = LocalDateTime.now();
        this.totalRemainingPrayers = totalRemainingPrayers; // Set the new field
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public long getTotalCalendarDays() {
        return totalCalendarDays;
    }

    public void setTotalCalendarDays(long totalCalendarDays) {
        this.totalCalendarDays = totalCalendarDays;
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

    public long getExcludedPeriodDays() {
        return excludedPeriodDays;
    }

    public void setExcludedPeriodDays(long excludedPeriodDays) {
        this.excludedPeriodDays = excludedPeriodDays;
    }

    public long getFinalMissedDaysForCalculation() {
        return finalMissedDaysForCalculation;
    }

    public void setFinalMissedDaysForCalculation(long finalMissedDaysForCalculation) {
        this.finalMissedDaysForCalculation = finalMissedDaysForCalculation;
    }

    public long getFajrCount() {
        return fajrCount;
    }

    public void setFajrCount(long fajrCount) {
        this.fajrCount = fajrCount;
    }

    public long getZuhrCount() {
        return zuhrCount;
    }

    public void setZuhrCount(long zuhrCount) {
        this.zuhrCount = zuhrCount;
    }

    public long getAsrCount() {
        return asrCount;
    }

    public void setAsrCount(long asrCount) {
        this.asrCount = asrCount;
    }

    public long getMaghribCount() {
        return maghribCount;
    }

    public void setMaghribCount(long maghribCount) {
        this.maghribCount = maghribCount;
    }

    public long getIshaCount() {
        return ishaCount;
    }

    public void setIshaCount(long ishaCount) {
        this.ishaCount = ishaCount;
    }

    public long getWitrCount() {
        return witrCount;
    }

    public void setWitrCount(long witrCount) {
        this.witrCount = witrCount;
    }

    public LocalDateTime getCalculationTimestamp() {
        return calculationTimestamp;
    }

    public void setCalculationTimestamp(LocalDateTime calculationTimestamp) {
        this.calculationTimestamp = calculationTimestamp;
    }

    // Updated: Getter and Setter for totalRemainingPrayers
    public long getTotalRemainingPrayers() {
        return totalRemainingPrayers;
    }

    public void setTotalRemainingPrayers(long totalRemainingPrayers) {
        this.totalRemainingPrayers = totalRemainingPrayers;
    }
}