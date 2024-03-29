package com.car.dealership.web;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.dealership.domain.dto.CarDTO;
import com.car.dealership.domain.dto.CarImagesAsPathsDTO;
import com.car.dealership.domain.entities.Car;
import com.car.dealership.service.CarService;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials="true")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    List<CarImagesAsPathsDTO> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping
    CarDTO addCar(@RequestBody CarDTO car) {
        return carService.addCar(car);
    }

    @GetMapping("/{id}")
    CarImagesAsPathsDTO getSingleCar(@PathVariable String id) {
        return this.carService.getById(id);
    }

    @PutMapping
    CarImagesAsPathsDTO modifyCar(@RequestBody CarDTO modifiedCar) {
        return this.carService.updateCar(modifiedCar);
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable String id) {
        this.carService.deleteCar(id);
    }

    @PostMapping("/{id}/buy")
    void buyCar(@PathVariable String id) {
        this.carService.buyCar(id);
    }

}
