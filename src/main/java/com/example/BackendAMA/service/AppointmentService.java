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

    
    public boolean bookAppointment(Appointment appointment) {
        if (appointmentRepository.existsByMechanicIdAndDateAndTime(
                appointment.getMechanicId(), appointment.getDate(), appointment.getTime())) {
            return false; 
        }

        appointmentRepository.save(appointment);

        String emailContent = "Dear Customer,\n\nYour appointment has been confirmed.\n\n" +
            "Details:\n" +
            "Service Status Number: " + appointment.getServiceStatusNumber() + "\n" +
            "Mechanic: " + appointment.getMechanicName() + "\n" +
            "Date: " + appointment.getDate() + "\n" +
            "Time: " + appointment.getTime() + "\n" +
            "Service: " + appointment.getService() + "\n\n" +
            "Thank you for choosing Automobile Avengers!";
        emailService.sendEmail(appointment.getCustomerEmail(), "Appointment Confirmation", emailContent);
        return true;
    }

    public Appointment getAppointmentByServiceStatusNumber(String serviceStatusNumber) {
        Appointment appointment = appointmentRepository.findByServiceStatusNumber(serviceStatusNumber);
        if (appointment == null) {
            System.out.println("Service Status Number not found: " + serviceStatusNumber);
        } else {
            System.out.println("Found Appointment: " + appointment);
        }
        return appointment;
    }


    public List<String> getAvailableTimes(Long mechanicId, String date) {
        List<Appointment> existingAppointments = appointmentRepository.findByMechanicIdAndDate(mechanicId, date);

        List<String> allTimes = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        while (startTime.isBefore(endTime)) {
            allTimes.add(startTime.toString());
            startTime = startTime.plusHours(1);
        }

        for (Appointment appointment : existingAppointments) {
            allTimes.remove(appointment.getTime());
        }
        return allTimes;
    }


    
    public List<Appointment> getAppointmentsByMechanicId(Long mechanicId) {
        return appointmentRepository.findByMechanicId(mechanicId);
    }

   
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

   
    public void cancelAppointment(Long appointmentId, String reason) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

       
        String emailContent = "Dear Customer,\n\nYour appointment scheduled on " + appointment.getDate() +
                " at " + appointment.getTime() + " has been cancelled. Reason: " + reason +
                ".\n\nThank you,\nAutomobile Avengers";

        emailService.sendEmail(appointment.getCustomerEmail(), "Appointment Cancellation", emailContent);

        
        appointmentRepository.delete(appointment);
    }
}
