// src/main/java/com/qalbconnect/qalbconnect_backend/repository/QazaPrayerRepository.java
package com.qalbconnect.qalbconnect_backend.repository;

import com.qalbconnect.qalbconnect_backend.model.QazaPrayer;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface QazaPrayerRepository extends MongoRepository<QazaPrayer, String> {

    // Custom query method to find a QazaPrayer record by userId
    // Since each user will likely have only one active Qaza record, Optional is appropriate.
    Optional<QazaPrayer> findByUserId(String userId);

    // You could also add methods like:
    // List<QazaPrayer> findAllByUserId(String userId); // If a user could have multiple records (e.g., historical)
}