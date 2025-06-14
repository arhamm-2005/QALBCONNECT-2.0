// src/main/java/com/qalbconnect/qalbconnect_backend/repository/VerseRepository.java
package com.qalbconnect.qalbconnect_backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.qalbconnect.qalbconnect_backend.model.Verse;

@Repository
public interface VerseRepository extends MongoRepository<Verse, String> {
    // Custom method to find verses by mood
    // Spring Data MongoDB automatically creates the query: db.verses_by_mood.find({ "mood": ? })
    List<Verse> findByMood(String mood);

    // To implement the @PostConstruct logic to check if the collection is empty
    long countBy(); // Simple count of all documents
}