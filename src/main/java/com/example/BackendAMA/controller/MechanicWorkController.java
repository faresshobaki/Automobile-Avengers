package com.example.BackendAMA.controller;

import com.example.BackendAMA.model.MechanicWork;
import com.example.BackendAMA.dto.LoginRequest;
import com.example.BackendAMA.service.MechanicWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicWorkController {

    @Autowired
    private MechanicWorkService mechanicWorkService;

    @GetMapping
    public ResponseEntity<List<MechanicWork>> getAllMechanics() {
        try {
            List<MechanicWork> mechanics = mechanicWorkService.getAllMechanics();
            return ResponseEntity.ok(mechanics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{id}/clock-in")
    public ResponseEntity<String> clockIn(@PathVariable Long id) {
        try {
            boolean success = mechanicWorkService.clockIn(id, LocalDateTime.now());
            if (success) {
                return ResponseEntity.ok("Clock-in successful");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Clock-in failed: Mechanic not found or already clocked in.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during clock-in: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/clock-out")
    public ResponseEntity<String> clockOut(@PathVariable Long id) {
        try {
            boolean success = mechanicWorkService.clockOut(id);
            if (success) {
                return ResponseEntity.ok("Clock-out successful");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Clock-out failed: Mechanic not found or already clocked out.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during clock-out: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateHourlyRate(@PathVariable Long id, @RequestBody MechanicWork updatedMechanic) {
        try {
            boolean success = mechanicWorkService.updateHourlyRate(id, updatedMechanic.getHourlyRate());
            return success ? ResponseEntity.ok("Hourly rate updated successfully")
                           : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mechanic not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update hourly rate");
        }
    }

    @PostMapping
    public ResponseEntity<String> createMechanic(@RequestBody MechanicWork newMechanic) {
        try {
            mechanicWorkService.createMechanic(newMechanic);
            return ResponseEntity.ok("Mechanic created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create mechanic");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<MechanicWork> login(@RequestBody LoginRequest loginRequest) {
        try {
            MechanicWork mechanic = mechanicWorkService.verifyCredentials(loginRequest.getUsername(), loginRequest.getPassword());
            return mechanic != null
                ? ResponseEntity.ok(mechanic)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}/credentials")
    public ResponseEntity<String> updateCredentials(@PathVariable Long id, @RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            boolean success = mechanicWorkService.updateCredentials(id, username, password);
            return success
                ? ResponseEntity.ok("Credentials updated successfully")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMechanic(@PathVariable Long id) {
        try {
            boolean success = mechanicWorkService.deleteMechanic(id);
            return success
                ? ResponseEntity.ok("Mechanic deleted successfully")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mechanic not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete mechanic");
        }
    }
}



    

