package com.example.crud.crud.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table()

public class Car {
    @Id
    @SequenceGenerator(name = "car_sequence", sequenceName = "car_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_sequence")
    @NotNull
    private Long id_car;

    @NotEmpty
    private String model;

    @NotEmpty
    private String brandS;

    @PastOrPresent
    private LocalDate dateOfProduction;

    private File photo;

    @Transient
    private Integer age;

    @ManyToOne
    @JoinColumn(name ="id_brand")
    private Brand brand;

    public Car(String model, String brandS, LocalDate dateOfProduction) {
        this.model = model;
        this.brandS = brandS;
        this.dateOfProduction = dateOfProduction;
    }
    public Car(String model, String brandS, LocalDate dateOfProduction, Brand brand) {
        this.model = model;
        this.brandS = brandS;
        this.dateOfProduction = dateOfProduction;
        this.brand = brand;
    }

    public Car(Long id_car, String model, String brandS, LocalDate dateOfProduction) {
        this.id_car = id_car;
        this.model = model;
        this.brandS = brandS;
        this.dateOfProduction = dateOfProduction;
    }

    public Car() {
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id_car + ", model='" + model + '\'' + ", brand='" + brandS + '\'' + ", dateOfProduction=" + dateOfProduction + '}';
    }

    public Long getId_car() {
        return id_car;
    }

    public void setId_car(Long id_car) {
        this.id_car = id_car;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrandS() {
        return brandS;
    }

    public void setBrandS(String brandS) {
        this.brandS = brandS;
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


    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public Brand getBrand() {
        return brand;
    }
    public String getBrandName(){
        return brand.getName();
    }
    public void setBrandName(String brandName){
        brand.setName(brandName);
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
