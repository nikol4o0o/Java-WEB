package com.car.dealership.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dealership.domain.entities.Car;
import com.car.dealership.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAllCars() {
        return this.carRepository.findAll();
    }

    @Override
    public Car getById(String id) {
        return this.carRepository.findById(id).get();
    }

    @Override
    public void deleteCar(String id) {
        this.carRepository.deleteById(id);
    }

    @Override
    public Car addCar(Car car) {
        return this.carRepository.saveAndFlush(car);
    }

    @Override
    public Car updateCar(String id, Car modifiedCar) {
        return this.carRepository.findById(id)
                .map(car -> {
                    car.setBrand(modifiedCar.getBrand());
                    car.setColor(modifiedCar.getColor());
                    car.setEmissionStandard(modifiedCar.getEmissionStandard());
                    car.setTruckCapacity(modifiedCar.getTruckCapacity());
                    car.setType(modifiedCar.getType());
                    car.setHorsePower(modifiedCar.getHorsePower());
                    car.setManufactureYear(modifiedCar.getManufactureYear());
                    car.setModelName(modifiedCar.getModelName());
                    car.setPrice(modifiedCar.getPrice());
                    car.setTransmission(modifiedCar.getTransmission());
                    return this.carRepository.save(car);
                })
                .orElseGet(() -> {
                    modifiedCar.setId(id);
                    return this.carRepository.save(modifiedCar);
                });
    }

}
