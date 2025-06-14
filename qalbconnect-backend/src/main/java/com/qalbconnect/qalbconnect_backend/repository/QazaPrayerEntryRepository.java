package com.qalbconnect.qalbconnect_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.qalbconnect.qalbconnect_backend.model.QazaPrayerEntry;

@Repository
public interface QazaPrayerEntryRepository extends MongoRepository<QazaPrayerEntry, String> { // Changed to String ID for MongoDB

    /**
     * Finds all QazaPrayerEntry records associated with a specific username,
     * ordered by calculation timestamp in descending order (most recent first).
     *
     * @param username The username for whom to retrieve the Qaza prayer entries.
     * @return A list of QazaPrayerEntry objects for the given user.
     */
    List<QazaPrayerEntry> findByUsernameOrderByCalculationTimestampDesc(String username);

    /**
     * Finds the single latest QazaPrayerEntry for a given user.
     *
     * @param username The username for whom to retrieve the latest Qaza prayer entry.
     * @return An Optional containing the latest QazaPrayerEntry, or empty if none exists.
     */
    Optional<QazaPrayerEntry> findTopByUsernameOrderByCalculationTimestampDesc(String username);
}