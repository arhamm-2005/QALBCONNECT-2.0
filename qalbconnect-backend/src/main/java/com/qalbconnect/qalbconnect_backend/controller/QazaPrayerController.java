// src/main/java/com/qalbconnect/qalbconnect_backend/controller/QazaPrayerController.java
package com.qalbconnect.qalbconnect_backend.controller;

import com.qalbconnect.qalbconnect_backend.model.QazaPrayer;
import com.qalbconnect.qalbconnect_backend.service.QazaPrayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/qaza") // Base path for all Qaza-e-Umri related endpoints
public class QazaPrayerController {

    @Autowired
    private QazaPrayerService qazaPrayerService;

    // DTO for calculation request (similar to AuthenticationRequest)
    // You'd ideally create a dedicated DTO class for this, but for brevity,
    // we'll use a Map or create a simple inner class/record for now.
    // Example DTO:
    public static class QazaCalculationRequest {
        private String gender;
        private String balighDate;
        private String endDate;
        private int periodDays;
        private int monthlyCyclesMissed;

        // Getters and Setters (or use Lombok @Data)
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        public String getBalighDate() { return balighDate; }
        public void setBalalighDate(String balighDate) { this.balighDate = balighDate; }
        public String getEndDate() { return endDate; }
        public void setEndDate(String endDate) { this.endDate = endDate; }
        public int getPeriodDays() { return periodDays; }
        public void setPeriodDays(int periodDays) { this.periodDays = periodDays; }
        public int getMonthlyCyclesMissed() { return monthlyCyclesMissed; }
        public void setMonthlyCyclesMissed(int monthlyCyclesMissed) { this.monthlyCyclesMissed = monthlyCyclesMissed; }
    }

    // DTO for adjustment request
    public static class QazaAdjustmentRequest {
        private String prayerName;
        private long adjustmentValue;

        // Getters and Setters (or use Lombok @Data)
        public String getPrayerName() { return prayerName; }
        public void setPrayerName(String prayerName) { this.prayerName = prayerName; }
        public long getAdjustmentValue() { return adjustmentValue; }
        public void setAdjustmentValue(long adjustmentValue) { this.adjustmentValue = adjustmentValue; }
    }


    /**
     * Endpoint to calculate and save/update a user's Qaza prayer record.
     * Accessible only by authenticated users (as per SecurityConfig.java).
     *
     * @param request The QazaCalculationRequest object containing calculation parameters.
     * @return The created or updated QazaPrayer record.
     */
    @PostMapping("/calculate")
    public ResponseEntity<?> calculateQaza(@RequestBody QazaCalculationRequest request) {
        try {
            QazaPrayer qaza = qazaPrayerService.calculateAndSaveQaza(
                request.getGender(),
                request.getBalighDate(),
                request.getEndDate(),
                request.getPeriodDays(),
                request.getMonthlyCyclesMissed()
            );
            return ResponseEntity.ok(qaza);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (IllegalStateException e) {
            // This catches the case where user ID is not found in security context
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * Endpoint to retrieve the authenticated user's Qaza prayer record.
     * Accessible only by authenticated users.
     *
     * @return The user's QazaPrayer record or 404 Not Found if none exists.
     */
    @GetMapping("/my-prayers")
    public ResponseEntity<?> getMyQazaPrayers() {
        try {
            Optional<QazaPrayer> qaza = qazaPrayerService.getQazaPrayersForCurrentUser();
            if (qaza.isPresent()) {
                return ResponseEntity.ok(qaza.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No Qaza prayer record found for this user. Please calculate first."));
            }
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * Endpoint to adjust a specific prayer count for the authenticated user.
     * Accessible only by authenticated users.
     *
     * @param request The QazaAdjustmentRequest object containing prayer name and adjustment value.
     * @return The updated QazaPrayer record.
     */
    @PostMapping("/adjust")
    public ResponseEntity<?> adjustQazaPrayer(@RequestBody QazaAdjustmentRequest request) {
        try {
            QazaPrayer updatedQaza = qazaPrayerService.adjustPrayerCount(
                request.getPrayerName(),
                request.getAdjustmentValue()
            );
            return ResponseEntity.ok(updatedQaza);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }

    // You could also add a PUT or PATCH endpoint for bulk updates/adjustments if needed
}