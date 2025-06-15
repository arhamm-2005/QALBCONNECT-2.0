// src/main/java/com/qalbconnect/qalbconnect_backend/model/QazaPrayer.java
package com.qalbconnect.qalbconnect_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "qaza_prayers") // This defines the collection name in MongoDB
public class QazaPrayer {

    @Id
    private String id; // MongoDB auto-generates this ID

    private String userId; // To link this record to a specific User

    // Initial calculation parameters (optional, but good for auditing/re-calculation)
    private String gender;
    private String balighDate; // Stored as String for simplicity, can be LocalDate if needed
    private String endDate;    // Stored as String
    private int periodDays;
    private int monthlyCyclesMissed;
    private long totalDaysCalculated; // Total days between baligh and end date
    private long excludedPeriodDays;  // Days excluded for female menstruation

    // The actual Qaza prayer counts
    private long fajr;
    private long zuhr;
    private long asr;
    private long maghrib;
    private long isha;
    private long witr;

    // You can add timestamps for creation and last update if needed for auditing
    // private LocalDateTime createdAt;
    // private LocalDateTime lastUpdatedAt;
}