package com.car.dealership.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    public Car() {
    }

    private String modelName;

    private String brand;

    private String transmission;

    private String type;

    private String color;

    private Integer manufactureYear;

    private Integer emissionStandard;

    private Integer horsePower;

    private Integer truckCapacity;

    private Double price;

    private Set<CarPicture> carPictures;

    private Dealership dealership;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public Integer getEmissionStandard() {
        return emissionStandard;
    }

    public void setEmissionStandard(Integer emissionStandard) {
        this.emissionStandard = emissionStandard;
    }

    public Integer getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
    }

    public Integer getTruckCapacity() {
        return truckCapacity;
    }

    public void setTruckCapacity(Integer truckCapacity) {
        this.truckCapacity = truckCapacity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "carpictureid")
    public Set<CarPicture> getCarPictures() {
        return carPictures;
    }

    public void setCarPictures(Set<CarPicture> carPictures) {
        this.carPictures = carPictures;
    }

    @ManyToOne
    @JoinColumn(name = "dealershipid", nullable = false)
    public Dealership getDealership() {
        return dealership;
    }

    public void setDealership(Dealership dealership) {
        this.dealership = dealership;
    }

    public void addPicture(CarPicture picture){
        picture.setCar(this);
        carPictures.add(picture);
    }
}
