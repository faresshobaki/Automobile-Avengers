package service;

import model.MechanicWork;
import repository.MechanicWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanicWorkService {

    @Autowired
    private MechanicWorkRepository repository;

    public List<MechanicWork> getAllMechanics() {
        return repository.findAll();
    }

    public MechanicWork addMechanicWork(MechanicWork mechanicWork) {
        return repository.save(mechanicWork);
    }

    public MechanicWork calculateSalary(Long id) {
        MechanicWork mechanicWork = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mechanic not found"));
        return mechanicWork;
    }
}