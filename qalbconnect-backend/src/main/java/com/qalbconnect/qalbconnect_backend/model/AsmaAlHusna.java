// src/main/java/com/qalbconnect/qalbconnect_backend/model/AsmaAlHusna.java
package com.qalbconnect.qalbconnect_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "asmaulhusna")
public class AsmaAlHusna {
    @Id
    private String id;
    private String arabic;
    private String englishMeaning;

    // Constructors, Getters, and Setters
    public AsmaAlHusna() {}

    public AsmaAlHusna(String arabic, String englishMeaning) {
        this.arabic = arabic;
        this.englishMeaning = englishMeaning;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getArabic() { return arabic; }
    public void setArabic(String arabic) { this.arabic = arabic; }
    public String getEnglishMeaning() { return englishMeaning; }
    public void setEnglishMeaning(String englishMeaning) { this.englishMeaning = englishMeaning; }
}