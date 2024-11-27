package com.example.BackendAMA.repository;

import com.example.BackendAMA.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByMechanicIdAndDate(Long mechanicId, String date);
    List<Appointment> findByMechanicId(Long mechanicId);
    List<Appointment> findAll();
    boolean existsByMechanicIdAndDateAndTime(Long mechanicId, String date, String time);
}
