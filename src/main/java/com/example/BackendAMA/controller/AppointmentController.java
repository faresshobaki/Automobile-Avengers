package com.example.BackendAMA.controller;

import com.example.BackendAMA.model.MechanicWork;
import com.example.BackendAMA.model.Appointment;
import com.example.BackendAMA.service.MechanicWorkService;
import com.example.BackendAMA.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment) {
        appointmentService.bookAppointment(appointment);
        return ResponseEntity.ok("Appointment booked successfully");
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
    @GetMapping("/admin/appointment")
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
