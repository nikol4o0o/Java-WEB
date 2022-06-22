package com.car.dealership.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.car.dealership.domain.dto.CarDTO;
import com.car.dealership.domain.entities.Car;
import com.car.dealership.domain.entities.CarPicture;
import com.car.dealership.repository.CarRepository;
import com.car.dealership.repository.DealershipRepository;

@Service
public class CarServiceImpl implements CarService {

    private static final String UPLOADED_FOLDER =  Paths.get(System.getProperty("user.dir")) + "\\pictures";;

    private final CarRepository carRepository;
    private final DealershipRepository dealershipRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, DealershipRepository dealershipRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.dealershipRepository = dealershipRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CarDTO> getAllCars() {
        return this.carRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public CarDTO getById(String id) {
        Optional<Car> car = this.carRepository.findById(id);
        if(car.isEmpty()){
            throw new NullPointerException("Car not found!");
        }
        return convertToDto(car.get());
    }

    @Override
    public void deleteCar(String id) {
        //todo delete car pictures
        this.carRepository.deleteById(id);
    }

    @Override
    public CarDTO addCar(CarDTO car) {
        for (MultipartFile picture :car.getPictures()) {
            if (!picture.isEmpty()) {
                try{
                    byte[] bytes = picture.getBytes();
                    Path path = Paths.get(UPLOADED_FOLDER +"\\"+ picture.getOriginalFilename());
                    if (!Files.exists(path.getParent()))
                        Files.createDirectories(path.getParent());
                    Files.write(path, bytes);
                }catch (Exception e){
                    System.out.println("Cannot save picture");
                }

            }
        }
        Car carEntity = convertToEntity(car);
        Set<CarPicture> pictures = new HashSet<>();
        carEntity.setCarPictures(pictures);
        // carEntity.setCarPictures(null);
        //Car dbCar = this.carRepository.saveAndFlush(carEntity);

        for (MultipartFile picture :car.getPictures()) {
            CarPicture carPicture = new CarPicture();
            carPicture.setPicturePath(Paths.get(UPLOADED_FOLDER +"\\"+  picture.getOriginalFilename()).toString());
            carEntity.addPicture(carPicture);
        }
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
        Car car = modelMapper.map(carDto, Car.class);
        String dealershipId = carDto.getDealership();
        if(dealershipId == null || dealershipRepository.findById(dealershipId).isEmpty()){
           throw new NullPointerException("No dealership found!");
        }
        car.setDealership(dealershipRepository.findById(dealershipId).get());
        return car;
    }
}
