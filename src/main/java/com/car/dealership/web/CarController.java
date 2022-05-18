package com.car.dealership.web;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.dealership.domain.entities.Car;
import com.car.dealership.service.CarService;

@RestController
@RequestMapping("cars")
public class CarController extends BaseController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping
    Car addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @GetMapping("/{id}")
    Car getSingleCar(@PathVariable String id) {
        return this.carService.getById(id);
    }

    @PutMapping("/{id}")
    Car modifyCar(@RequestBody Car modifiedCar, @PathVariable String id) {
        return this.carService.updateCar(id, modifiedCar);
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable String id) {
        this.carService.deleteCar(id);
    }

}
