package com.example.BackendAMA.repository;

import com.example.BackendAMA.model.MechanicWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MechanicWorkRepository extends JpaRepository<MechanicWork, Long> {}