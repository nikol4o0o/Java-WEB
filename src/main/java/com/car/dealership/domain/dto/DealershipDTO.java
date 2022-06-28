package com.car.dealership.domain.dto;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.car.dealership.domain.entities.BaseEntity;
import com.car.dealership.domain.entities.Car;
import com.car.dealership.domain.entities.User;

public class DealershipDTO {

    private String id;
    private String name;
    private Integer yearFounded;
    private String companyOwner;
    private Set<Car> cars;
    private String user;

    public DealershipDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(Integer yearFounded) {
        this.yearFounded = yearFounded;
    }

    public String getCompanyOwner() {
        return companyOwner;
    }

    public void setCompanyOwner(String companyOwner) {
        this.companyOwner = companyOwner;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
