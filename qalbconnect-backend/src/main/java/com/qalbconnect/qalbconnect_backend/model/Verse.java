// src/main/java/com/qalbconnect/qalbconnect_backend/model/Verse.java
package com.qalbconnect.qalbconnect_backend.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "verses_by_mood")
public class Verse {
    @Id
    private String id;
    private String mood; // e.g., "Happiness", "Sadness", "Patience"
    private String arabicText;
    private String englishTranslation;
    private String reference; // e.g., "Quran 2:153"
    private List<String> keywords; // Optional: for more complex searching

    // Constructors
    public Verse() {
    }

    public Verse(String mood, String arabicText, String englishTranslation, String reference, List<String> keywords) {
        this.mood = mood;
        this.arabicText = arabicText;
        this.englishTranslation = englishTranslation;
        this.reference = reference;
        this.keywords = keywords;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getArabicText() {
        return arabicText;
    }

    public void setArabicText(String arabicText) {
        this.arabicText = arabicText;
    }

    public String getEnglishTranslation() {
        return englishTranslation;
    }

    public void setEnglishTranslation(String englishTranslation) {
        this.englishTranslation = englishTranslation;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "Verse{" +
               "id='" + id + '\'' +
               ", mood='" + mood + '\'' +
               ", arabicText='" + arabicText + '\'' +
               ", englishTranslation='" + englishTranslation + '\'' +
               ", reference='" + reference + '\'' +
               ", keywords=" + keywords +
               '}';
    }
}