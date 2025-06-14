package com.qalbconnect.qalbconnect_backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qalbconnect.qalbconnect_backend.dto.QazaCalculatorRequestDto; // Import List
import com.qalbconnect.qalbconnect_backend.dto.QazaCalculatorResponseDto;
import com.qalbconnect.qalbconnect_backend.dto.QazaPrayerAddDto;
import com.qalbconnect.qalbconnect_backend.dto.QazaPrayerUpdateDto;
import com.qalbconnect.qalbconnect_backend.model.QazaPrayerEntry;
import com.qalbconnect.qalbconnect_backend.repository.QazaPrayerEntryRepository;

@Service
public class QazaPrayerService {

    private final QazaPrayerEntryRepository qazaPrayerEntryRepository;

    @Autowired
    public QazaPrayerService(QazaPrayerEntryRepository qazaPrayerEntryRepository) {
        this.qazaPrayerEntryRepository = qazaPrayerEntryRepository;
    }

    /**
     * Calculates the missed Qaza prayers based on the provided request DTO.
     * This method saves the calculation result as a new entry for the user.
     *
     * @param requestDto The DTO containing calculation parameters.
     * @param username   The username for whom the calculation is being performed.
     * @return A QazaCalculatorResponseDto with the calculation results.
     */
    public List<QazaCalculatorResponseDto> getQazaPrayerHistory(String username) {
        List<QazaPrayerEntry> historyEntries = qazaPrayerEntryRepository.findByUsernameOrderByCalculationTimestampDesc(username);
        return historyEntries.stream()
                             .map(QazaCalculatorResponseDto::fromEntity) // Convert each entity to DTO
                             .collect(Collectors.toList());
    }
    public QazaCalculatorResponseDto calculateQazaPrayers(QazaCalculatorRequestDto requestDto, String username) {
        LocalDate startDate = requestDto.getStartDate();
        LocalDate endDate = requestDto.getEndDate();
        String gender = requestDto.getGender();
        Integer averagePeriodDays = requestDto.getAveragePeriodDays();
        Integer totalMonthlyCycles = requestDto.getTotalMonthlyCycles();

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Ending date cannot be before starting date!");
        }

        long totalCalendarDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        long excludedPeriodDays = 0;
        long finalMissedDaysForCalculation;

        if ("female".equalsIgnoreCase(gender)) {
            if (averagePeriodDays == null || totalMonthlyCycles == null || averagePeriodDays < 3 || averagePeriodDays > 10) {
                throw new IllegalArgumentException("For female gender, average period days (3-10) and total monthly cycles are required.");
            }
            excludedPeriodDays = (long) totalMonthlyCycles * averagePeriodDays;
            finalMissedDaysForCalculation = totalCalendarDays - excludedPeriodDays;
            if (finalMissedDaysForCalculation < 0) {
                finalMissedDaysForCalculation = 0;
            }
        } else if ("male".equalsIgnoreCase(gender)) {
            finalMissedDaysForCalculation = totalCalendarDays;
        } else {
            throw new IllegalArgumentException("Gender must be 'male' or 'female'.");
        }

        // Calculate individual prayer counts (assuming 1 prayer per missed day for each type)
        long fajrCount = finalMissedDaysForCalculation;
        long zuhrCount = finalMissedDaysForCalculation;
        long asrCount = finalMissedDaysForCalculation;
        long maghribCount = finalMissedDaysForCalculation;
        long ishaCount = finalMissedDaysForCalculation;
        long witrCount = finalMissedDaysForCalculation;

        // Calculate total remaining prayers
        long totalRemainingPrayers = fajrCount + zuhrCount + asrCount + maghribCount + ishaCount + witrCount;

        // Create and save the new QazaPrayerEntry
        QazaPrayerEntry newEntry = new QazaPrayerEntry(
                username,
                startDate,
                endDate,
                totalCalendarDays,
                gender,
                averagePeriodDays,
                totalMonthlyCycles,
                excludedPeriodDays,
                finalMissedDaysForCalculation,
                fajrCount,
                zuhrCount,
                asrCount,
                maghribCount,
                ishaCount,
                witrCount,
                totalRemainingPrayers // Pass the calculated totalRemainingPrayers
        );
        newEntry.setCalculationTimestamp(LocalDateTime.now()); // Ensure timestamp is set correctly

        QazaPrayerEntry savedEntry = qazaPrayerEntryRepository.save(newEntry);

        // Convert the saved entity to a response DTO
        QazaCalculatorResponseDto responseDto = QazaCalculatorResponseDto.fromEntity(savedEntry);
        responseDto.setStatusMessage("Qaza prayer calculation successful.");

        return responseDto;
    }

    /**
     * Retrieves the latest Qaza prayer entry for a given username.
     *
     * @param username The username.
     * @return An Optional containing the latest QazaPrayerEntry, or empty if none exists.
     */
    public Optional<QazaPrayerEntry> getLatestQazaPrayerEntry(String username) {
        return qazaPrayerEntryRepository.findTopByUsernameOrderByCalculationTimestampDesc(username);
    }

    /**
     * Adds (increments) specific Qaza prayer counts to the latest entry for a user.
     * If no previous entry exists, a new one is created with initial counts.
     *
     * @param username The username for whom to add prayers.
     * @param addDto   DTO containing the number of prayers to add.
     * @return The updated QazaPrayerEntry as a DTO.
     */
    public QazaCalculatorResponseDto addMissedPrayers(String username, QazaPrayerAddDto addDto) {
        Optional<QazaPrayerEntry> latestEntryOpt = getLatestQazaPrayerEntry(username);
        QazaPrayerEntry entryToUpdate;

        if (latestEntryOpt.isPresent()) {
            entryToUpdate = latestEntryOpt.get();
        } else {
            // If no existing entry, create a new one with 0 counts for initial state
            entryToUpdate = new QazaPrayerEntry();
            entryToUpdate.setUsername(username);
            // Set default dates or null, as this is a manual add, not a calculation
            entryToUpdate.setStartDate(null);
            entryToUpdate.setEndDate(null);
            entryToUpdate.setTotalCalendarDays(0);
            entryToUpdate.setGender("unknown"); // Default or require on first entry
            entryToUpdate.setFinalMissedDaysForCalculation(0);
        }

        entryToUpdate.setFajrCount(entryToUpdate.getFajrCount() + addDto.getFajrMissed());
        entryToUpdate.setZuhrCount(entryToUpdate.getZuhrCount() + addDto.getZuhrMissed());
        entryToUpdate.setAsrCount(entryToUpdate.getAsrCount() + addDto.getAsrMissed());
        entryToUpdate.setMaghribCount(entryToUpdate.getMaghribCount() + addDto.getMaghribMissed());
        entryToUpdate.setIshaCount(entryToUpdate.getIshaCount() + addDto.getIshaMissed());
        entryToUpdate.setWitrCount(entryToUpdate.getWitrCount() + addDto.getWitrMissed());

        // Recalculate total remaining prayers
        long newTotalRemainingPrayers = entryToUpdate.getFajrCount() + entryToUpdate.getZuhrCount() +
                                        entryToUpdate.getAsrCount() + entryToUpdate.getMaghribCount() +
                                        entryToUpdate.getIshaCount() + entryToUpdate.getWitrCount();
        entryToUpdate.setTotalRemainingPrayers(newTotalRemainingPrayers);
        entryToUpdate.setCalculationTimestamp(LocalDateTime.now()); // Update timestamp

        QazaPrayerEntry savedEntry = qazaPrayerEntryRepository.save(entryToUpdate);
        QazaCalculatorResponseDto responseDto = QazaCalculatorResponseDto.fromEntity(savedEntry);
        responseDto.setStatusMessage("Missed Qaza prayers added successfully.");
        return responseDto;
    }

    /**
     * Updates (decrements) specific Qaza prayer counts from the latest entry for a user.
     * Ensures counts do not go below zero.
     *
     * @param username The username for whom to update prayers.
     * @param updateDto DTO containing the number of prayers performed.
     * @return The updated QazaPrayerEntry as a DTO, or null if no entry exists.
     */
    public QazaCalculatorResponseDto updatePrayersPerformed(String username, QazaPrayerUpdateDto updateDto) {
        Optional<QazaPrayerEntry> latestEntryOpt = getLatestQazaPrayerEntry(username);

        if (latestEntryOpt.isEmpty()) {
            throw new IllegalStateException("No existing Qaza prayer entry found for user: " + username);
        }

        QazaPrayerEntry entryToUpdate = latestEntryOpt.get();

        entryToUpdate.setFajrCount(Math.max(0, entryToUpdate.getFajrCount() - updateDto.getFajrPrayed()));
        entryToUpdate.setZuhrCount(Math.max(0, entryToUpdate.getZuhrCount() - updateDto.getZuhrPrayed()));
        entryToUpdate.setAsrCount(Math.max(0, entryToUpdate.getAsrCount() - updateDto.getAsrPrayed()));
        entryToUpdate.setMaghribCount(Math.max(0, entryToUpdate.getMaghribCount() - updateDto.getMaghribPrayed()));
        entryToUpdate.setIshaCount(Math.max(0, entryToUpdate.getIshaCount() - updateDto.getIshaPrayed()));
        entryToUpdate.setWitrCount(Math.max(0, entryToUpdate.getWitrCount() - updateDto.getWitrPrayed()));

        // Recalculate total remaining prayers
        long newTotalRemainingPrayers = entryToUpdate.getFajrCount() + entryToUpdate.getZuhrCount() +
                                        entryToUpdate.getAsrCount() + entryToUpdate.getMaghribCount() +
                                        entryToUpdate.getIshaCount() + entryToUpdate.getWitrCount();
        entryToUpdate.setTotalRemainingPrayers(newTotalRemainingPrayers);
        entryToUpdate.setCalculationTimestamp(LocalDateTime.now()); // Update timestamp

        QazaPrayerEntry savedEntry = qazaPrayerEntryRepository.save(entryToUpdate);
        QazaCalculatorResponseDto responseDto = QazaCalculatorResponseDto.fromEntity(savedEntry);
        responseDto.setStatusMessage("Qaza prayers updated successfully.");
        return responseDto;
    }


    /**
     * Fetches the current outstanding Qaza prayer counts for a user.
     *
     * @param username The username.
     * @return A QazaCalculatorResponseDto with the latest prayer counts, or a default empty DTO if no entry exists.
     */
    public QazaCalculatorResponseDto getCurrentQazaCounts(String username) {
        Optional<QazaPrayerEntry> latestEntryOpt = getLatestQazaPrayerEntry(username);

        if (latestEntryOpt.isPresent()) {
            QazaPrayerEntry latestEntry = latestEntryOpt.get();
            QazaCalculatorResponseDto responseDto = QazaCalculatorResponseDto.fromEntity(latestEntry);
            responseDto.setStatusMessage("Latest Qaza prayer counts retrieved successfully.");
            return responseDto;
        } else {
            // Return an empty or default response if no entries exist for the user
            QazaCalculatorResponseDto defaultDto = new QazaCalculatorResponseDto();
            defaultDto.setStatusMessage("No Qaza prayer entries found for this user yet.");
            // You might want to initialize other counts to 0 here as well
            return defaultDto;
        }
    }
}