package com.github.livreprogramacao.registeringavehicle.infra.adapter.outbound.persistence.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String ownerName;

    @NotBlank
    @Pattern(
            regexp = "^[A-Z]{3}-?[0-9][A-Z0-9][0-9]{2}$",
            message = "Invalid license plate"
    )
    private String licensePlate;

    @NotBlank
    private String make;

    @NotBlank
    private String model;

    @Positive
    private int manufacturingYear;

    private LocalDate registrationDate;

    private LocalDate expirationDate;

    public Registration() {
    }

    public Registration(
            String ownerName,
            String licensePlate,
            String make,
            String model,
            int manufacturingYear,
            LocalDate registrationDate,
            LocalDate expirationDate) {

        this.ownerName = ownerName;
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.manufacturingYear = manufacturingYear;
        this.registrationDate = registrationDate;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(int manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}

