package com.example.crud.crud.service;

import com.example.crud.crud.entity.Car;
import com.example.crud.crud.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }
    public List<Car> getCarsByBrand(String filter) {
        return carRepository.search(filter);
    }

    public void addCar(Car car) {
        carRepository.save(car);
    }

    public void deleteCarById(Long id) {
        carRepository.deleteById(id);

    }    public void deleteCar(Car car) {
        carRepository.delete(car);
    }

    @Transactional
    public void updateBrand(Long carId, String model) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new IllegalStateException("student does not exist"));
        car.setModel(model);
    }
}
