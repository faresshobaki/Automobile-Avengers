package controller;

import model.MechanicWork;
import service.MechanicWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicWorkController {

    @Autowired
    private MechanicWorkService service;

    @GetMapping
    public List<MechanicWork> getAllMechanics() {
        return service.getAllMechanics();
    }

    @PostMapping
    public MechanicWork addMechanicWork(@RequestBody MechanicWork mechanicWork) {
        return service.addMechanicWork(mechanicWork);
    }

    @GetMapping("/{id}/calculate-salary")
    public MechanicWork calculateSalary(@PathVariable Long id) {
        return service.calculateSalary(id);
    }
}