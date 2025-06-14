package com.qalbconnect.qalbconnect_backend.dto;

import com.qalbconnect.qalbconnect_backend.model.QazaPrayerEntry;
import java.time.format.DateTimeFormatter;

public class QazaCalculatorResponseDto {
    private long totalCalendarDays; // Total days between dates, before gender-specific exclusions
    private String gender;
    private Integer averagePeriodDays;
    private Integer totalMonthlyCycles;
    private long excludedPeriodDays;
    private long finalMissedDaysForCalculation; // This is the base for Fajr, Zuhr, etc.

    private long fajrCount;
    private long zuhrCount;
    private long asrCount;
    private long maghribCount;
    private long ishaCount;
    private long witrCount;
    private String statusMessage;
    private String startDateString;
    private String endDateString;
    private String calculationTimestampString;

    // NEW FIELD: Sum of all individual Qaza prayer counts (Fajr+Zuhr+Asr+Maghrib+Isha+Witr)
    private long totalRemainingPrayers;

    // Default constructor
    public QazaCalculatorResponseDto() {
    }

    // Full constructor (useful for new calculations)
    public QazaCalculatorResponseDto(long totalCalendarDays, String gender, Integer averagePeriodDays,
                                     Integer totalMonthlyCycles, long excludedPeriodDays,
                                     long finalMissedDaysForCalculation, long fajrCount, long zuhrCount,
                                     long asrCount, long maghribCount, long ishaCount, long witrCount,
                                     String statusMessage, String startDateString, String endDateString,
                                     String calculationTimestampString, long totalRemainingPrayers) {
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
        this.statusMessage = statusMessage;
        this.startDateString = startDateString;
        this.endDateString = endDateString;
        this.calculationTimestampString = calculationTimestampString;
        this.totalRemainingPrayers = totalRemainingPrayers;
    }

    // Static factory method to create DTO from entity
    public static QazaCalculatorResponseDto fromEntity(QazaPrayerEntry entity) {
        QazaCalculatorResponseDto dto = new QazaCalculatorResponseDto();
        dto.setTotalCalendarDays(entity.getTotalCalendarDays());
        dto.setGender(entity.getGender());
        dto.setAveragePeriodDays(entity.getAveragePeriodDays());
        dto.setTotalMonthlyCycles(entity.getTotalMonthlyCycles());
        dto.setExcludedPeriodDays(entity.getExcludedPeriodDays());
        dto.setFinalMissedDaysForCalculation(entity.getFinalMissedDaysForCalculation());
        dto.setFajrCount(entity.getFajrCount());
        dto.setZuhrCount(entity.getZuhrCount());
        dto.setAsrCount(entity.getAsrCount());
        dto.setMaghribCount(entity.getMaghribCount());
        dto.setIshaCount(entity.getIshaCount());
        dto.setWitrCount(entity.getWitrCount());
        dto.setTotalRemainingPrayers(entity.getTotalRemainingPrayers()); // Ensure this is set
        // Format dates for display
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        dto.setStartDateString(entity.getStartDate().format(formatter));
        dto.setEndDateString(entity.getEndDate().format(formatter));
        // Format timestamp for display
        DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
        dto.setCalculationTimestampString(entity.getCalculationTimestamp().format(timestampFormatter));
        // Status message will be set by the service layer, or can be added here if it's based purely on DTO data
        return dto;
    }

    // Getters and Setters
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

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public String getCalculationTimestampString() {
        return calculationTimestampString;
    }

    public void setCalculationTimestampString(String calculationTimestampString) {
        this.calculationTimestampString = calculationTimestampString;
    }

    // NEW: Getter and Setter for totalRemainingPrayers
    public long getTotalRemainingPrayers() {
        return totalRemainingPrayers;
    }

    public void setTotalRemainingPrayers(long totalRemainingPrayers) {
        this.totalRemainingPrayers = totalRemainingPrayers;
    }
}