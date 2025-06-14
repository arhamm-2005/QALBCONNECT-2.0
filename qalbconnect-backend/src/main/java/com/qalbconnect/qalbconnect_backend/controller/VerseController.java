// src/main/java/com/qalbconnect/qalbconnect_backend/controller/VerseController.java
package com.qalbconnect.qalbconnect_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qalbconnect.qalbconnect_backend.model.Verse;
import com.qalbconnect.qalbconnect_backend.service.VerseService;

@RestController
@RequestMapping("/api/versesbymood")
public class VerseController {

    private final VerseService verseService;

    @Autowired
    public VerseController(VerseService verseService) {
        this.verseService = verseService;
    }

    @GetMapping("/{mood}") // Handles GET requests to /api/versesbymood/{mood}
    public ResponseEntity<List<Verse>> getVersesByMood(@PathVariable String mood) {
        List<Verse> verses = verseService.getVersesByMood(mood);
        if (verses.isEmpty()) {
            // Return 404 Not Found if no verses for the mood
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(verses);
    }
}