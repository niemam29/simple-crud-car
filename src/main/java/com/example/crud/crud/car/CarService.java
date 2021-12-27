package com.example.crud.crud.car;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarService {
    public List<Car> getCars() {
        return List.of(new Car(Long.valueOf(0), "126p", "Fiat", LocalDate.of(2000, 12, 12)));
    }
}
