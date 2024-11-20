package com.example.BackendAMA.controller;

import com.example.BackendAMA.model.MechanicWork;
import com.example.BackendAMA.service.MechanicWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicWorkController {

    @Autowired
    private MechanicWorkService mechanicWorkService;

    // Fetch all mechanics
    @GetMapping
    public List<MechanicWork> getAllMechanics() {
        return mechanicWorkService.getAllMechanics();
    }

    // Log clock-in time
    @PostMapping("/{id}/clock-in")
    public String clockIn(@PathVariable Long id) {
        boolean success = mechanicWorkService.clockIn(id, LocalDateTime.now());
        return success ? "Clock-in successful!" : "Clock-in failed!";
    }

    // Log clock-out time
    @PostMapping("/{id}/clock-out")
    public String clockOut(@PathVariable Long id) {
        boolean success = mechanicWorkService.clockOut(id, LocalDateTime.now());
        return success ? "Clock-out successful!" : "Clock-out failed!";
    }
}
