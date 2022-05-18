package com.car.dealership.service;

import java.util.List;
import java.util.Set;

import com.car.dealership.domain.entities.Car;

public interface CarService {

    List<Car> getAllCars();

    Car getById(String id);

    void deleteCar(String id);

    Car addCar(Car car);

    Car updateCar(String id, Car modifiedCar);
}
