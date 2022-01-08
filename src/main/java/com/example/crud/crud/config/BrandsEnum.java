package com.example.crud.crud.config;

import com.example.crud.crud.entity.Brand;

import java.io.File;
import java.time.LocalDate;

public enum BrandsEnum {
    FIAT(new Brand(
            "Fiat",
            "Corporate Address\n" +
                    "Stellantis N.V.\n" +
                    "Taurusavenue 1\n" +
                    "2132 LS Hoofddorp, The Netherlands",
            LocalDate.of(1923, 10, 12), new File("src/main/webapp/defaultlogo.png"))),
    HONDA(new Brand(
            "Honda",
            "2-1-1 Minami-Aoyama,\n" +
                    " Minato-ku, \n" +
                    "Tokyo, \n" +
                    "107-8556,\n" +
                    "Japan",
            LocalDate.of(1923, 10, 12), new File("src/main/webapp/defaultlogo2.png")));
    private Brand brand;

    BrandsEnum(Brand brand) {
        this.brand = brand;
    }

    Brand getBrand() {
        return this.brand;
    }


}
