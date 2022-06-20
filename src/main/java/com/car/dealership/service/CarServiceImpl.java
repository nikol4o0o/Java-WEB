package com.car.dealership.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dealership.domain.dto.CarDTO;
import com.car.dealership.domain.entities.Car;
import com.car.dealership.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final DealershipService dealershipService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, DealershipService dealershipService, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.dealershipService = dealershipService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CarDTO> getAllCars() {
        return this.carRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public CarDTO getById(String id) {
        return convertToDto(this.carRepository.findById(id).get());
    }

    @Override
    public void deleteCar(String id) {
        this.carRepository.deleteById(id);
    }

    @Override
    public CarDTO addCar(CarDTO car) {
        Car carEntity = convertToEntity(car);
        this.carRepository.saveAndFlush(carEntity);
        return car;
    }

    @Override
    public CarDTO updateCar(String id, CarDTO modifiedCar) {
        return convertToDto(this.carRepository.findById(id)
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
                    Car carEntity = convertToEntity(modifiedCar);
                    this.carRepository.save(carEntity);
                    return carEntity;
                }));
    }

    private CarDTO convertToDto(Car car) {
        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        carDTO.setDealership(car.getDealership().getId());
        return carDTO;
    }

    private Car convertToEntity(CarDTO carDto) {
        // TODO add validation and check if it exists  ....
        Car car = modelMapper.map(carDto, Car.class);
        car.setDealership(dealershipService.getById(carDto.getDealership()));
        return car;
    }
}
