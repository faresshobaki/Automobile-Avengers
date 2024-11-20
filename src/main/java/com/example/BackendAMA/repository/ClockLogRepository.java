package com.example.BackendAMA.repository;

import com.example.BackendAMA.model.ClockLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClockLogRepository extends JpaRepository<ClockLog, Long> {
    ClockLog findTopByMechanicIdOrderByClockInDesc(Long mechanicId);
}
