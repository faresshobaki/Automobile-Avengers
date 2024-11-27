package com.example.BackendAMA.service;

import com.example.BackendAMA.model.Appointment;
import com.example.BackendAMA.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EmailService emailService;

    // Get available times for a mechanic on a specific date
    public List<String> getAvailableTimes(Long mechanicId, String date) {
        List<Appointment> existingAppointments = appointmentRepository.findByMechanicIdAndDate(mechanicId, date);

        // Define all possible times (9:00 AM to 5:00 PM in 1-hour intervals)
        List<String> allTimes = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0); // 9:00 AM
        LocalTime endTime = LocalTime.of(17, 0); // 5:00 PM
        while (startTime.isBefore(endTime)) {
            allTimes.add(startTime.toString());
            startTime = startTime.plusHours(1); // Increment by 1 hour
        }

        // Remove already booked times
        for (Appointment appointment : existingAppointments) {
            allTimes.remove(appointment.getTime());
        }

        return allTimes;
    }

    // Book an appointment
    public boolean bookAppointment(Appointment appointment) {
        // Check for double booking
        if (appointmentRepository.existsByMechanicIdAndDateAndTime(
                appointment.getMechanicId(), appointment.getDate(), appointment.getTime())) {
            return false; // Prevent double booking
        }

        appointmentRepository.save(appointment);
        return true;
    }

    // Get appointments for a specific mechanic
    public List<Appointment> getAppointmentsByMechanicId(Long mechanicId) {
        return appointmentRepository.findByMechanicId(mechanicId);
    }

    // Get all appointments for admin
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Cancel an appointment and send a cancellation email
    public void cancelAppointment(Long appointmentId, String reason) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Prepare cancellation email
        String emailContent = "Dear Customer,\n\nYour appointment scheduled on " + appointment.getDate() +
                " at " + appointment.getTime() + " has been cancelled. Reason: " + reason +
                ".\n\nThank you,\nAutomobile Avengers";

        emailService.sendEmail(appointment.getCustomerEmail(), "Appointment Cancellation", emailContent);

        // Delete the appointment from the database
        appointmentRepository.delete(appointment);
    }
}
