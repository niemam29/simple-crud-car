package com.example.crud.crud.controller;

import com.example.crud.crud.entity.Car;
import com.example.crud.crud.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foo")

public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> get() {
        return carService.getCars();
    }

    @PostMapping
    public void registerCar(@RequestBody Car car) {
        carService.addCar(car);
    }

    @DeleteMapping(path = "{carId}")
    public void delete(@PathVariable("carId") Long carId) {

        carService.deleteCarById(carId);
    }

    @PutMapping(path = "{carId}")
    public void updateCar(@PathVariable("carId") Long carId, @RequestParam() String model) {
        carService.updateBrand(carId, model);
    }
}
