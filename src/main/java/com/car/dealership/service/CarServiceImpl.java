package com.car.dealership.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import com.car.dealership.domain.dto.CarImagesAsPathsDTO;
import com.car.dealership.domain.entities.Car;
import com.car.dealership.domain.entities.CarPicture;
import com.car.dealership.repository.CarRepository;
import com.car.dealership.repository.DealershipRepository;

@Service
public class CarServiceImpl implements CarService {

    private static final String UPLOADED_FOLDER = Paths.get(System.getProperty("user.dir")) + "\\pictures";

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
    public List<CarImagesAsPathsDTO> getAllCars() {
        return this.carRepository.findAll().stream().map(this::convertToImagesDto).collect(Collectors.toList());
    }

    @Override
    public CarImagesAsPathsDTO getById(String id) {
        Optional<Car> car = this.carRepository.findById(id);
        if (car.isEmpty()) {
            throw new NullPointerException("Car not found!");
        }
        return convertToImagesDto(car.get());
    }

    @Override
    public void deleteCar(String id) {
        //todo delete car pictures
        this.carRepository.deleteById(id);
    }

    @Override
    public CarDTO addCar(CarDTO car) {
        savePictures(car);
        Car carEntity = convertToEntity(car);
        // carEntity.setCarPictures(null);
        //Car dbCar = this.carRepository.saveAndFlush(carEntity);

        Set<CarPicture> pictures = new HashSet<>();
        carEntity.setCarPictures(pictures);
        for (MultipartFile picture : car.getPictures()) {
            CarPicture carPicture = new CarPicture();
            carPicture.setPicturePath(Paths.get(UPLOADED_FOLDER + "\\" + picture.getOriginalFilename()).toString());
            carEntity.addPicture(carPicture);
        }
        this.carRepository.saveAndFlush(carEntity);
        return car;
    }

    private void savePictures(CarDTO car) {
        for (MultipartFile picture : car.getPictures()) {
            if (!picture.isEmpty()) {
                try {
                    byte[] bytes = picture.getBytes();
                    Path path = Paths.get(UPLOADED_FOLDER + "\\" + picture.getOriginalFilename());
                    if (!Files.exists(path.getParent()))
                        Files.createDirectories(path.getParent());
                    Files.write(path, bytes);
                } catch (Exception e) {
                    System.out.println("Cannot save picture");
                }

            }
        }
    }

    @Override
    public CarImagesAsPathsDTO updateCar(String id, CarDTO modifiedCar) {
        return convertToImagesDto(this.carRepository.findById(id)
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

                    savePictures(modifiedCar);
                    Set<CarPicture> pictures = new HashSet<>();
                    car.setCarPictures(pictures);
                    for (MultipartFile picture : modifiedCar.getPictures()) {
                        CarPicture carPicture = new CarPicture();
                        carPicture.setPicturePath(Paths.get(UPLOADED_FOLDER + "\\" + picture.getOriginalFilename()).toString());
                        car.addPicture(carPicture);
                    }

                    return this.carRepository.save(car);
                })
                .orElseGet(() -> {
                    modifiedCar.setId(id);
                    Car carEntity = convertToEntity(modifiedCar);
                    this.carRepository.save(carEntity);
                    return carEntity;
                }));
    }

    private CarImagesAsPathsDTO convertToImagesDto(Car car) {
        CarImagesAsPathsDTO carDTO = new CarImagesAsPathsDTO();
        carDTO.setId(car.getId());
        carDTO.setBrand(car.getBrand());
        carDTO.setColor(car.getColor());
        carDTO.setEmissionStandard(car.getEmissionStandard());
        carDTO.setTruckCapacity(car.getTruckCapacity());
        carDTO.setType(car.getType());
        carDTO.setHorsePower(car.getHorsePower());
        carDTO.setManufactureYear(car.getManufactureYear());
        carDTO.setModelName(car.getModelName());
        carDTO.setPrice(car.getPrice());
        carDTO.setTransmission(car.getTransmission());

        List<String> picturePaths = new ArrayList<>();
        Set<CarPicture> carPictures = car.getCarPictures();
        for (CarPicture pic : carPictures) {
            picturePaths.add(pic.getPicturePath());
        }
        carDTO.setPictures(picturePaths);
        carDTO.setDealership(car.getDealership().getId());
        return carDTO;
    }

    private Car convertToEntity(CarDTO carDto) {
        Car car = modelMapper.map(carDto, Car.class);
        String dealershipId = carDto.getDealership();
        if (dealershipId == null || dealershipRepository.findById(dealershipId).isEmpty()) {
            throw new NullPointerException("No dealership found!");
        }
        car.setDealership(dealershipRepository.findById(dealershipId).get());
        return car;
    }

}
