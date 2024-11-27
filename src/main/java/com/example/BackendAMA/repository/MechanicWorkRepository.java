package com.example.BackendAMA.repository;

import com.example.BackendAMA.model.MechanicWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface MechanicWorkRepository extends JpaRepository<MechanicWork, Long> {
	Optional <MechanicWork> findByMechanicId(String mechanicId);
	Optional<MechanicWork> findByUsernameAndPassword(String username, String password);
	Optional<MechanicWork> findById(Long id);

}