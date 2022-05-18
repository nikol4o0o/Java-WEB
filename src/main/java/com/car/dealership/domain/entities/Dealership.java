package com.car.dealership.domain.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dealerships")
public class Dealership extends BaseEntity {

    public Dealership() {
    }

    private String name;

    private Integer yearFounded;

    private String companyOwner;

    private Set<Car> cars;

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

    @OneToMany( mappedBy = "dealership")
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }


}
