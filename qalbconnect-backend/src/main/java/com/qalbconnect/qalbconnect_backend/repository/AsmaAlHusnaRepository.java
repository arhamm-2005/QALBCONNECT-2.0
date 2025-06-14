// src/main/java/com/qalbconnect/qalbconnect_backend/repository/AsmaAlHusnaRepository.java
package com.qalbconnect.qalbconnect_backend.repository;

import com.qalbconnect.qalbconnect_backend.model.AsmaAlHusna;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsmaAlHusnaRepository extends MongoRepository<AsmaAlHusna, String> {
}