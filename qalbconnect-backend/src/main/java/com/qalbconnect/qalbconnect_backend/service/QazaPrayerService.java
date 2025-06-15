// src/main/java/com/qalbconnect/qalbconnect_backend/service/QazaPrayerService.java
package com.qalbconnect.qalbconnect_backend.service;

import com.qalbconnect.qalbconnect_backend.model.QazaPrayer;
import com.qalbconnect.qalbconnect_backend.repository.QazaPrayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class QazaPrayerService {

    @Autowired
    private QazaPrayerRepository qazaPrayerRepository;

    // Helper method to get the current authenticated user's ID
    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            // Assuming your UserDetails implementation (your User model) has an getId() method
            // If your UserDetails is just Spring's default User, you might need a different approach
            // For QalbConnect's User.java, it has an 'id' field, so casting to User is appropriate.
            com.qalbconnect.qalbconnect_backend.model.User currentUser =
                    (com.qalbconnect.qalbconnect_backend.model.User) authentication.getPrincipal();
            return currentUser.getId();
        }
        throw new IllegalStateException("User not authenticated or user ID not found in security context.");
    }

    /**
     * Calculates and saves/updates Qaza prayer counts for the authenticated user.
     * If a record exists for the user, it updates it; otherwise, it creates a new one.
     * @param gender "male" or "female"
     * @param balighDateStr Date when the user became baligh (dd-MM-yyyy)
     * @param endDateStr Current or end date for calculation (dd-MM-yyyy)
     * @param periodDays Average period days (for females, 3-10)
     * @param monthlyCyclesMissed Total monthly cycles missed (for females)
     * @return The saved/updated QazaPrayer object.
     * @throws IllegalArgumentException if dates are invalid or periodDays are out of range.
     */
    public QazaPrayer calculateAndSaveQaza(String gender, String balighDateStr, String endDateStr,
                                            int periodDays, int monthlyCyclesMissed) {
        String userId = getCurrentUserId(); // Get ID of the authenticated user

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate balighDate, endDate;
        try {
            balighDate = LocalDate.parse(balighDateStr, formatter);
            endDate = LocalDate.parse(endDateStr, formatter);
        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use dd-MM-yyyy.");
        }

        if (balighDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Baligh date cannot be after the end date.");
        }

        if (gender.equalsIgnoreCase("female") && (periodDays < 3 || periodDays > 10)) {
            throw new IllegalArgumentException("Period days for female must be between 3 and 10.");
        }

        long totalDaysCalculated = ChronoUnit.DAYS.between(balighDate, endDate) + 1; // +1 to include end date

        long missedDays = totalDaysCalculated;
        long excludedPeriodDays = 0;

        if (gender.equalsIgnoreCase("female")) {
            excludedPeriodDays = (long) monthlyCyclesMissed * periodDays;
            missedDays = totalDaysCalculated - excludedPeriodDays;
            if (missedDays < 0) { // Ensure missed days don't go negative
                missedDays = 0;
            }
        }

        // Retrieve existing record or create a new one
        Optional<QazaPrayer> existingQaza = qazaPrayerRepository.findByUserId(userId);
        QazaPrayer qazaPrayer = existingQaza.orElse(new QazaPrayer());

        // Set or update fields
        qazaPrayer.setUserId(userId);
        qazaPrayer.setGender(gender);
        qazaPrayer.setBalighDate(balighDateStr);
        qazaPrayer.setEndDate(endDateStr);
        qazaPrayer.setPeriodDays(periodDays);
        qazaPrayer.setMonthlyCyclesMissed(monthlyCyclesMissed);
        qazaPrayer.setTotalDaysCalculated(totalDaysCalculated);
        qazaPrayer.setExcludedPeriodDays(excludedPeriodDays);

        // If this is a new calculation or first time for user, set initial counts
        // Otherwise, these might be updated via adjust methods later
        if (existingQaza.isEmpty()) {
            qazaPrayer.setFajr(missedDays);
            qazaPrayer.setZuhr(missedDays);
            qazaPrayer.setAsr(missedDays);
            qazaPrayer.setMaghrib(missedDays);
            qazaPrayer.setIsha(missedDays);
            qazaPrayer.setWitr(missedDays);
        } else {
            // If updating, you might want to adjust counts based on the *new* missedDays
            // or leave them as they are, expecting explicit adjustments.
            // For simplicity, we'll re-calculate if existing.
            qazaPrayer.setFajr(missedDays);
            qazaPrayer.setZuhr(missedDays);
            qazaPrayer.setAsr(missedDays);
            qazaPrayer.setMaghrib(missedDays);
            qazaPrayer.setIsha(missedDays);
            qazaPrayer.setWitr(missedDays);
        }


        return qazaPrayerRepository.save(qazaPrayer);
    }

    /**
     * Retrieves the Qaza prayer record for the authenticated user.
     * @return An Optional containing the QazaPrayer object if found, or empty.
     */
    public Optional<QazaPrayer> getQazaPrayersForCurrentUser() {
        String userId = getCurrentUserId();
        return qazaPrayerRepository.findByUserId(userId);
    }

    /**
     * Adjusts the count for a specific prayer for the authenticated user.
     * @param prayerName The name of the prayer (e.g., "Fajr", "Zuhr", "Asr", "Maghrib", "Isha", "Witr").
     * @param adjustmentValue The value to adjust by (e.g., +5, -3).
     * @return The updated QazaPrayer object.
     * @throws IllegalArgumentException if prayerName is invalid or user record not found.
     */
    public QazaPrayer adjustPrayerCount(String prayerName, long adjustmentValue) {
        String userId = getCurrentUserId();
        QazaPrayer qazaPrayer = qazaPrayerRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Qaza prayer record not found for user. Please calculate first."));

        switch (prayerName.toLowerCase()) {
            case "fajr":
                qazaPrayer.setFajr(Math.max(0, qazaPrayer.getFajr() + adjustmentValue));
                break;
            case "zuhr":
                qazaPrayer.setZuhr(Math.max(0, qazaPrayer.getZuhr() + adjustmentValue));
                break;
            case "asr":
                qazaPrayer.setAsr(Math.max(0, qazaPrayer.getAsr() + adjustmentValue));
                break;
            case "maghrib":
                qazaPrayer.setMaghrib(Math.max(0, qazaPrayer.getMaghrib() + adjustmentValue));
                break;
            case "isha":
                qazaPrayer.setIsha(Math.max(0, qazaPrayer.getIsha() + adjustmentValue));
                break;
            case "witr":
                qazaPrayer.setWitr(Math.max(0, qazaPrayer.getWitr() + adjustmentValue));
                break;
            default:
                throw new IllegalArgumentException("Invalid prayer name: " + prayerName);
        }

        return qazaPrayerRepository.save(qazaPrayer);
    }
}