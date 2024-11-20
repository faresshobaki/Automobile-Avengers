package com.example.BackendAMA.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mechanics") // Ensures table name consistency
public class MechanicWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mechanicName")
    private String mechanicName;

    @Column(name = "mechanicId")
    private String mechanicId;

    @Column(name = "hourlyRate")
    private Double hourlyRate;

    @Column(name = "hoursWorked")
    private Double hoursWorked = 0.00;

    @Column(name = "taxRate")
    private Double taxRate = 0.08; // Default value

    @Column(name = "username", unique = true, nullable = false)
    private String username; // New field for login username

    @Column(name = "password", nullable = false)
    private String password; // New field for login password

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Utility Methods
    public Double calculateGrossSalary() {
        return hourlyRate * hoursWorked;
    }

    public Double calculateNetSalary() {
        return calculateGrossSalary() - (calculateGrossSalary() * taxRate);
    }
}
