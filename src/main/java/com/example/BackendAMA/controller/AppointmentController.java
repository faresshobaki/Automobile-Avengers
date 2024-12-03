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
        String serviceStatusNumber = UUID.randomUUID().toString(); // Generate unique status number
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
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
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
    }
