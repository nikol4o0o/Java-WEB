package com.car.dealership.domain.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "CarPicture")
@Table(name = "picture")
@JsonIgnoreProperties({"car"})
public class CarPicture {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    private String picturePath;

    public CarPicture() {
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CarPicture))
            return false;
        return id != null && id.equals(((CarPicture) o).getId());
    }
}
