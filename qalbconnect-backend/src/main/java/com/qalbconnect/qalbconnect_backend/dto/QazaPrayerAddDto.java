package com.qalbconnect.qalbconnect_backend.dto;

/**
 * DTO for adding missed Qaza prayer counts.
 * This object will carry the number of prayers a user has missed
 * for each type (Fajr, Zuhr, etc.) to increment the outstanding Qaza count.
 */
public class QazaPrayerAddDto {

    private Integer fajrMissed = 0;
    private Integer zuhrMissed = 0;
    private Integer asrMissed = 0;
    private Integer maghribMissed = 0;
    private Integer ishaMissed = 0;
    private Integer witrMissed = 0;

    // Getters and Setters
    public Integer getFajrMissed() {
        return fajrMissed;
    }

    public void setFajrMissed(Integer fajrMissed) {
        this.fajrMissed = (fajrMissed != null) ? fajrMissed : 0;
    }

    public Integer getZuhrMissed() {
        return zuhrMissed;
    }

    public void setZuhrMissed(Integer zuhrMissed) {
        this.zuhrMissed = (zuhrMissed != null) ? zuhrMissed : 0;
    }

    public Integer getAsrMissed() {
        return asrMissed;
    }

    public void setAsrMissed(Integer asrMissed) {
        this.asrMissed = (asrMissed != null) ? asrMissed : 0;
    }

    public Integer getMaghribMissed() {
        return maghribMissed;
    }

    public void setMaghribMissed(Integer maghribMissed) {
        this.maghribMissed = (maghribMissed != null) ? maghribMissed : 0;
    }

    public Integer getIshaMissed() {
        return ishaMissed;
    }

    public void setIshaMissed(Integer ishaMissed) {
        this.ishaMissed = (ishaMissed != null) ? ishaMissed : 0;
    }

    public Integer getWitrMissed() {
        return witrMissed;
    }

    public void setWitrMissed(Integer witrMissed) {
        this.witrMissed = (witrMissed != null) ? witrMissed : 0;
    }
}