package com.example.BackendAMA.service;

import com.example.BackendAMA.model.ClockLog;
import com.example.BackendAMA.model.MechanicWork;
import com.example.BackendAMA.repository.ClockLogRepository;
import com.example.BackendAMA.repository.MechanicWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        Optional<MechanicWork> mechanicOptional = mechanicWorkRepository.findById(mechanicId);
        if (mechanicOptional.isEmpty()) {
            return false;
        }

        ClockLog lastLog = clockLogRepository.findTopByMechanicIdOrderByClockInDesc(mechanicId);
        if (lastLog != null && lastLog.getClockOut() == null) {
            return false;
        }

        ClockLog clockLog = new ClockLog();
        clockLog.setMechanicId(mechanicId);
        clockLog.setClockIn(clockInTime);
        clockLogRepository.save(clockLog);

        return true;
    }

    public boolean clockOut(Long mechanicId) {
        Optional<MechanicWork> mechanicOptional = mechanicWorkRepository.findById(mechanicId);
        if (mechanicOptional.isEmpty()) {
            return false;
        }

        ClockLog lastLog = clockLogRepository.findTopByMechanicIdOrderByClockInDesc(mechanicId);
        if (lastLog == null || lastLog.getClockOut() != null) {
            return false;
        }

        lastLog.setClockOut(LocalDateTime.now());

        Duration duration = Duration.between(lastLog.getClockIn(), lastLog.getClockOut());
        double hoursWorked = duration.toMinutes() / 60.0;

        MechanicWork mechanic = mechanicOptional.get();
        mechanic.setHoursWorked(mechanic.getHoursWorked() + hoursWorked);

        clockLogRepository.save(lastLog);
        mechanicWorkRepository.save(mechanic);

        return true;
    }

    public MechanicWork verifyCredentials(String username, String password) {
        return mechanicWorkRepository.findByUsernameAndPassword(username, password).orElse(null);
    }



    public boolean updateHourlyRate(Long id, double hourlyRate) {
        Optional<MechanicWork> mechanicOpt = mechanicWorkRepository.findById(id);
        if (mechanicOpt.isPresent()) {
            MechanicWork mechanic = mechanicOpt.get();
            mechanic.setHourlyRate(hourlyRate);
            mechanicWorkRepository.save(mechanic);
            return true;
        }
        return false;
    }

    public void createMechanic(MechanicWork newMechanic) {
        mechanicWorkRepository.save(newMechanic);
  
    }

    public boolean updateCredentials(Long id, String username, String password) {
        Optional<MechanicWork> mechanicOpt = mechanicWorkRepository.findById(id);
        if (mechanicOpt.isPresent()) {
            MechanicWork mechanic = mechanicOpt.get();
            mechanic.setUsername(username);
            mechanic.setPassword(password);
            mechanicWorkRepository.save(mechanic);
            return true;
        }
        return false;
    }

    public boolean deleteMechanic(Long id) {
        if (mechanicWorkRepository.existsById(id)) {
            mechanicWorkRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

