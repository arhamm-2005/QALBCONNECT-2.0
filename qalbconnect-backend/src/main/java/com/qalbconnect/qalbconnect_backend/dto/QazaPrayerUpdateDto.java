package com.qalbconnect.qalbconnect_backend.dto;

/**
 * DTO for updating Qaza prayer counts.
 * This object will carry the number of prayers a user has performed
 * for each type (Fajr, Zuhr, etc.) to decrement the outstanding Qaza count.
 */
public class QazaPrayerUpdateDto {

    private Integer fajrPrayed = 0;
    private Integer zuhrPrayed = 0;
    private Integer asrPrayed = 0;
    private Integer maghribPrayed = 0;
    private Integer ishaPrayed = 0;
    private Integer witrPrayed = 0;

    // Getters and Setters
    public Integer getFajrPrayed() {
        return fajrPrayed;
    }

    public void setFajrPrayed(Integer fajrPrayed) {
        this.fajrPrayed = (fajrPrayed != null) ? fajrPrayed : 0;
    }

    public Integer getZuhrPrayed() {
        return zuhrPrayed;
    }

    public void setZuhrPrayed(Integer zuhrPrayed) {
        this.zuhrPrayed = (zuhrPrayed != null) ? zuhrPrayed : 0;
    }

    public Integer getAsrPrayed() {
        return asrPrayed;
    }

    public void setAsrPrayed(Integer asrPrayed) {
        this.asrPrayed = (asrPrayed != null) ? asrPrayed : 0;
    }

    public Integer getMaghribPrayed() {
        return maghribPrayed;
    }

    public void setMaghribPrayed(Integer maghribPrayed) {
        this.maghribPrayed = (maghribPrayed != null) ? maghribPrayed : 0;
    }

    public Integer getIshaPrayed() {
        return ishaPrayed;
    }

    public void setIshaPrayed(Integer ishaPrayed) {
        this.ishaPrayed = (ishaPrayed != null) ? ishaPrayed : 0;
    }

    public Integer getWitrPrayed() {
        return witrPrayed;
    }

    public void setWitrPrayed(Integer witrPrayed) {
        this.witrPrayed = (witrPrayed != null) ? witrPrayed : 0;
    }
}