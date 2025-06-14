// src/main/java/com/qalbconnect/qalbconnect_backend/repository/ProphetStoryRepository.java
package com.qalbconnect.qalbconnect_backend.repository;

import com.qalbconnect.qalbconnect_backend.model.ProphetStory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProphetStoryRepository extends MongoRepository<ProphetStory, String> {
    // Spring Data MongoDB automatically provides CRUD operations.
    // We might add custom query methods here if needed in the future,
    // e.g., findByName(String name)
}