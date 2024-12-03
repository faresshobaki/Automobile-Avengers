package com.example.BackendAMA.controller;

import com.example.BackendAMA.model.MechanicWork;
import com.example.BackendAMA.model.Appointment;
import com.example.BackendAMA.service.MechanicWorkService;
import com.example.BackendAMA.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private MechanicWorkService mechanicWorkService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/mechanics")
    public ResponseEntity<List<MechanicWork>> getAvailableMechanics() {
        return ResponseEntity.ok(mechanicWorkService.getAllMechanics());
    }
    @GetMapping("/available-times")
    public ResponseEntity<List<String>> getAvailableTimes(
            @RequestParam Long mechanicId,
            @RequestParam String date) {
        List<String> availableTimes = appointmentService.getAvailableTimes(mechanicId, date);
        return ResponseEntity.ok(availableTimes);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> bookAppointment(@RequestBody Appointment appointment) {
        // Generate a shorter numeric service status number
        String serviceStatusNumber = generateServiceStatusNumber();
        appointment.setServiceStatusNumber(serviceStatusNumber);

        boolean success = appointmentService.bookAppointment(appointment);

        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("serviceStatusNumber", serviceStatusNumber);
            response.put("message", "Appointment booked successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Appointment slot is already booked.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Helper method to generate a shorter numeric service status number
    private String generateServiceStatusNumber() {
        Random random = new Random();
        int number = random.nextInt(900000) + 100000; // Generate a 6-digit number between 100000 and 999999
        return String.valueOf(number);
    }

    @GetMapping("/service-status/{serviceStatusNumber}")
    public ResponseEntity<Appointment> getServiceStatus(@PathVariable String serviceStatusNumber) {
        System.out.println("Received Service Status Number: " + serviceStatusNumber);

        Appointment appointment = appointmentService.getAppointmentByServiceStatusNumber(serviceStatusNumber);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        } else {
            System.out.println("No appointment found for Service Status Number: " + serviceStatusNumber);
            return ResponseEntity.status(404).build();
        }
    }



    @GetMapping("/{mechanicId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByMechanic(@PathVariable Long mechanicId) {
        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByMechanicId(mechanicId);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/admin/appointments")
    public ResponseEntity<Map<String, Object>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        Map<String, Object> response = new HashMap<>();
        response.put("appointments", appointments);
        response.put("count", appointments.size());  // Add the count of appointments to the response
        return ResponseEntity.ok(response);
    }


        

        // Cancel an appointment
        @PostMapping("/{appointmentId}/cancel")
        public ResponseEntity<Void> cancelAppointment(
                @PathVariable Long appointmentId,
                @RequestBody Map<String, String> request) {
            String reason = request.get("reason");

            if (reason == null || reason.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            appointmentService.cancelAppointment(appointmentId, reason);
            return ResponseEntity.ok().build();
        }
        @PutMapping("/service-status")
        public ResponseEntity<String> updateServiceStatus(@RequestBody UpdateStatusRequest request) {
            try {
                appointmentService.updateServiceStatus(request.getServiceStatusNumber(), request.getStatus());
                return ResponseEntity.ok("Service status updated successfully.");
            } catch (Exception e) {
                return ResponseEntity.status(400).body("Failed to update service status: " + e.getMessage());
            }
        }

        public static class UpdateStatusRequest {
            private String serviceStatusNumber;
            private String status;

            public String getServiceStatusNumber() {
                return serviceStatusNumber;
            }

            public void setServiceStatusNumber(String serviceStatusNumber) {
                this.serviceStatusNumber = serviceStatusNumber;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }