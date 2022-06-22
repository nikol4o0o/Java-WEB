package com.car.dealership.service;

import java.util.List;
import java.util.Set;

import com.car.dealership.domain.dto.CarDTO;
import com.car.dealership.domain.entities.Car;

public interface CarService {

    List<CarDTO> getAllCars();

    CarDTO getById(String id);

    void deleteCar(String id);

    CarDTO addCar(CarDTO car);

    CarDTO updateCar(String id, CarDTO modifiedCar);
}
