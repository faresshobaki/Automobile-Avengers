package com.example.BackendAMA.service;

import com.example.BackendAMA.model.ClockLog;
import com.example.BackendAMA.model.MechanicWork;
import com.example.BackendAMA.repository.ClockLogRepository;
import com.example.BackendAMA.repository.MechanicWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MechanicWorkService {

    @Autowired
    private MechanicWorkRepository mechanicWorkRepository;

    @Autowired
    private ClockLogRepository clockLogRepository;

    public List<MechanicWork> getAllMechanics() {
        return mechanicWorkRepository.findAll();
    }

    public boolean clockIn(Long mechanicId, LocalDateTime clockInTime) {
        MechanicWork mechanic = mechanicWorkRepository.findById(mechanicId).orElse(null);
        if (mechanic == null) {
            return false;
        }

        ClockLog clockLog = new ClockLog();
        clockLog.setMechanicId(mechanicId);
        clockLog.setClockIn(clockInTime);
        clockLogRepository.save(clockLog);

        return true;
    }

    public boolean clockOut(Long mechanicId, LocalDateTime clockOutTime) {
        MechanicWork mechanic = mechanicWorkRepository.findById(mechanicId).orElse(null);
        if (mechanic == null) {
            return false;
        }

        ClockLog clockLog = clockLogRepository.findTopByMechanicIdOrderByClockInDesc(mechanicId);
        if (clockLog == null || clockLog.getClockOut() != null) {
            return false; // No open clock-in record
        }

        clockLog.setClockOut(clockOutTime);
        long hoursWorked = java.time.Duration.between(clockLog.getClockIn(), clockOutTime).toHours();
        mechanic.setHoursWorked(mechanic.getHoursWorked() + hoursWorked);

        clockLogRepository.save(clockLog);
        mechanicWorkRepository.save(mechanic);

        return true;
    }
}
