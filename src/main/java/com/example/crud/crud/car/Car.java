package com.example.crud.crud.car;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table()

public class Car {
    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @NotNull
    private Long id;

    @NotEmpty
    private String model;

    @NotEmpty
    private String brand;

    @PastOrPresent
    private LocalDate dateOfProduction;

    @Transient
    private Integer age;

    public Car(String model, String brand, LocalDate dateOfProduction) {
        this.model = model;
        this.brand = brand;
        this.dateOfProduction = dateOfProduction;
    }

    public Car(Long id, String model, String brand, LocalDate dateOfProduction) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.dateOfProduction = dateOfProduction;
    }

    public Car() {
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", model='" + model + '\'' + ", brand='" + brand + '\'' + ", dateOfProduction=" + dateOfProduction + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public LocalDate getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(LocalDate dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

    public Integer getAge() {
        return Period.between(this.dateOfProduction, LocalDate.now()).getYears();
    }


}
