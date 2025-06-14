// src/main/java/com/qalbconnect/qalbconnect_backend/controller/AsmaAlHusnaController.java
package com.qalbconnect.qalbconnect_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin; // <--- Import this
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qalbconnect.qalbconnect_backend.model.AsmaAlHusna;
import com.qalbconnect.qalbconnect_backend.service.AsmaAlHusnaService;

@RestController
@RequestMapping("/api/namesofallah")
// Add this annotation with the origin of your frontend server
@CrossOrigin(origins = "http://127.0.0.1:5500") // <--- Add this line
public class AsmaAlHusnaController {

    private final AsmaAlHusnaService asmaAlHusnaService;

    public AsmaAlHusnaController(AsmaAlHusnaService asmaAlHusnaService) {
        this.asmaAlHusnaService = asmaAlHusnaService;
    }

    @GetMapping
    public List<AsmaAlHusna> getAllNames() {
        return asmaAlHusnaService.getAllAsmaAlHusna();
    }
}