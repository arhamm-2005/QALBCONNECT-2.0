// src/main/java/com/qalbconnect/qalbconnect_backend/controller/ProphetStoryController.java
package com.qalbconnect.qalbconnect_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qalbconnect.qalbconnect_backend.model.ProphetStory;
import com.qalbconnect.qalbconnect_backend.service.ProphetStoryService;

@RestController
@RequestMapping("/api/prophetstories")
public class ProphetStoryController {

    private final ProphetStoryService prophetStoryService;

    public ProphetStoryController(ProphetStoryService prophetStoryService) {
        this.prophetStoryService = prophetStoryService;
    }

    @GetMapping
    public List<ProphetStory> getAllProphetStories() {
        return prophetStoryService.getAllProphetStories();
    }
}