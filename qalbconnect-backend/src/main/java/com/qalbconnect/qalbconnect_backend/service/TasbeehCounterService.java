// src/main/java/com/qalbconnect/qalbconnect_backend/service/TasbeehCounterService.java
package com.qalbconnect.qalbconnect_backend.service;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct; // Import for initialization

@Service
public class TasbeehCounterService {

    private int counter; // In-memory counter

    // Initialize the counter when the service is created
    @PostConstruct
    public void init() {
        this.counter = 0; // Default starting value
        System.out.println("TasbeehCounterService initialized. Counter set to 0.");
    }

    public int getCounter() {
        return counter;
    }

    public int increment() {
        counter++;
        System.out.println("Counter incremented to: " + counter);
        return counter;
    }

    public int decrement() {
        if (counter > 0) { // Prevent negative counts
            counter--;
        }
        System.out.println("Counter decremented to: " + counter);
        return counter;
    }

    public int reset() {
        counter = 0;
        System.out.println("Counter reset to: " + counter);
        return counter;
    }
}