package com.example.crud.crud.entity;

import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.File;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table

public class Brand {

    @Id
    @SequenceGenerator(name = "brand_sequence", sequenceName = "brand_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_sequence")
    @NotNull
    private Long id_brand;

    @NotEmpty
    @Column(unique = true)
    public String name;

    @NotEmpty
    private String address;

    @PastOrPresent
    private LocalDate yearOfCreation;

    @OneToMany(mappedBy = "brand")
    private Set<Car> cars;

    @NotNull
    private File logo;

    @Transient
    private int age;

    public Brand(String name, String address, LocalDate yearOfCreation) {
        this.name = name;
        this.address = address;
        this.yearOfCreation = yearOfCreation;
    }
    public Brand(String name, String address, LocalDate yearOfCreation,File logo) {
        this.logo = logo;
        this.name = name;
        this.address = address;
        this.yearOfCreation = yearOfCreation;
    }

    public Brand(Long id_brand, String name, String address, LocalDate yearOfCreation) {
        this.id_brand = id_brand;
        this.name = name;
        this.address = address;
        this.yearOfCreation = yearOfCreation;
    }

    public Brand() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(LocalDate yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getId_brand() {
        return id_brand;
    }

    public void setId_brand(Long id_brand) {
        this.id_brand = id_brand;
    }
}
