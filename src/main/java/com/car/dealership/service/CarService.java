package com.car.dealership.service;

import java.util.List;

import com.car.dealership.domain.dto.CarDTO;
import com.car.dealership.domain.dto.CarImagesAsPathsDTO;

public interface CarService {

    List<CarImagesAsPathsDTO> getAllCars();

    CarImagesAsPathsDTO getById(String id);

    void deleteCar(String id);

    CarDTO addCar(CarDTO car);

    CarImagesAsPathsDTO updateCar(String id, CarDTO modifiedCar);
}
