// src/main/java/com/qalbconnect/qalbconnect_backend/controller/TasbeehCounterController.java
package com.qalbconnect.qalbconnect_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qalbconnect.qalbconnect_backend.service.TasbeehCounterService;

@RestController
@RequestMapping("/api/tasbeeh")
public class TasbeehCounterController {

    private final TasbeehCounterService tasbeehCounterService;

    @Autowired
    public TasbeehCounterController(TasbeehCounterService tasbeehCounterService) {
        this.tasbeehCounterService = tasbeehCounterService;
    }

    @GetMapping("/current")
    public int getCurrentCount() {
        return tasbeehCounterService.getCounter();
    }

    @PostMapping("/count")
    public int incrementCount() {
        return tasbeehCounterService.increment();
    }

    @PostMapping("/undo")
    public int decrementCount() {
        return tasbeehCounterService.decrement();
    }

    @PostMapping("/reset")
    public int resetCount() {
        return tasbeehCounterService.reset();
    }
}