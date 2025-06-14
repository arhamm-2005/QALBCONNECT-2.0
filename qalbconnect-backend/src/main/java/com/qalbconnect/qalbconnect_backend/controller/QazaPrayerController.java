package com.qalbconnect.qalbconnect_backend.controller; // Adjust package as per your project structure

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qalbconnect.qalbconnect_backend.dto.QazaCalculatorRequestDto;
import com.qalbconnect.qalbconnect_backend.dto.QazaCalculatorResponseDto;
import com.qalbconnect.qalbconnect_backend.dto.QazaPrayerAddDto;
import com.qalbconnect.qalbconnect_backend.dto.QazaPrayerUpdateDto;
import com.qalbconnect.qalbconnect_backend.service.QazaPrayerService;

@RestController
@RequestMapping("/api/qaza")
// @CrossOrigin(origins = "http://localhost:8080") // Ensure CORS is configured here or globally
public class QazaPrayerController {

    private final QazaPrayerService qazaPrayerService;

    public QazaPrayerController(QazaPrayerService qazaPrayerService) {
        this.qazaPrayerService = qazaPrayerService;
    }

    @GetMapping("/latest")
    public ResponseEntity<QazaCalculatorResponseDto> getLatestQazaCounts(Principal principal) {
        String username = principal.getName();
        // Changed to call getCurrentQazaCounts from the service
        QazaCalculatorResponseDto latestEntry = qazaPrayerService.getCurrentQazaCounts(username);
        if (latestEntry != null) { // Service returns a default DTO if not found, so check its status or content
             if ("No Qaza prayer entries found for this user yet.".equals(latestEntry.getStatusMessage())) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(latestEntry);
        } else {
            return ResponseEntity.noContent().build(); // Should ideally not happen if service returns default DTO
        }
    }

    @PostMapping("/calculate")
    public ResponseEntity<QazaCalculatorResponseDto> calculateAndSaveQaza(
            @RequestBody QazaCalculatorRequestDto requestDto, Principal principal) {
        String username = principal.getName();
        // Changed to call calculateQazaPrayers from the service
        QazaCalculatorResponseDto response = qazaPrayerService.calculateQazaPrayers(requestDto, username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<QazaCalculatorResponseDto> updateQaza(
            @RequestBody QazaPrayerUpdateDto updateDto, Principal principal) {
        String username = principal.getName();
        // Changed to call updatePrayersPerformed from the service with correct parameter order
        QazaCalculatorResponseDto response = qazaPrayerService.updatePrayersPerformed(username, updateDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<QazaCalculatorResponseDto> addMissedQaza(
            @RequestBody QazaPrayerAddDto addDto, Principal principal) {
        String username = principal.getName();
        // Changed to call addMissedPrayers from the service with correct parameter order
        QazaCalculatorResponseDto response = qazaPrayerService.addMissedPrayers(username, addDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<QazaCalculatorResponseDto>> getQazaHistory(Principal principal) {
        String username = principal.getName();
        List<QazaCalculatorResponseDto> history = qazaPrayerService.getQazaPrayerHistory(username);
        return ResponseEntity.ok(history);
    }
}