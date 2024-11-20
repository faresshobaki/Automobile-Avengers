package com.example.BackendAMA.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mechanic_work") // Ensures table name consistency
public class MechanicWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @Column(name = "mechanic_name", nullable = false) // Explicitly map column
    private String mechanicName;

    @Column(name = "mechanic_id", nullable = false, unique = true) // Ensure unique mechanic ID
    private String mechanicId;

    @Column(name = "hourly_rate", nullable = false) // Ensures hourlyRate column is not null
    private Double hourlyRate;

    @Column(name = "hours_worked", nullable = false) // Ensures hoursWorked column is not null
    private Double hoursWorked;

    @Column(name = "tax_rate", nullable = false) // Ensures taxRate column is not null
    private Double taxRate;

    // Default Constructor
    public MechanicWork() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }

    public String getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(String mechanicId) {
        this.mechanicId = mechanicId;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    // Utility Methods
    public Double calculateGrossSalary() {
        return hourlyRate * hoursWorked;
    }

    public Double calculateNetSalary() {
        return calculateGrossSalary() - (calculateGrossSalary() * taxRate);
    }
}
