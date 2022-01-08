package com.example.crud.crud.config;

import com.example.crud.crud.entity.Car;
import com.example.crud.crud.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class CarConfig {
    @Bean
    CommandLineRunner commandLineRunner(CarRepository carRepository) {
        return args -> {
            Car fiacior = new Car(
                    "126p", "Fiat", LocalDate.of(1980, 10, 12), BrandsEnum.FIAT.getBrand());

            Car fiacior2 = new Car(
                    "125p", "Fiat", LocalDate.of(1940, 10, 12), BrandsEnum.HONDA.getBrand());

            carRepository.saveAll(List.of(fiacior, fiacior2));
        };
    }
}
