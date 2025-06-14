// src/main/java/com/qalbconnect/qalbconnect_backend/model/ProphetStory.java
package com.qalbconnect.qalbconnect_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "prophet_stories")
public class ProphetStory {
    @Id
    private String id;
    private String name;
    private List<String> segments;

    // Default constructor for Spring Data MongoDB
    public ProphetStory() {
        this.segments = new ArrayList<>();
    }

    // Constructor with name and segments
    public ProphetStory(String name, List<String> segments) {
        this.name = name;
        this.segments = segments != null ? new ArrayList<>(segments) : new ArrayList<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getSegments() {
        return segments;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSegments(List<String> segments) {
        this.segments = segments != null ? new ArrayList<>(segments) : new ArrayList<>();
    }

    // Builder pattern for easier construction
    public static class Builder {
        private String name;
        private List<String> segments = new ArrayList<>();

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder addSegment(String segment) {
            this.segments.add(segment);
            return this;
        }

        public ProphetStory build() {
            return new ProphetStory(name, segments);
        }
    }
}