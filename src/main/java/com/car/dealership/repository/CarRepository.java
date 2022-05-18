package com.car.dealership.repository;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.dealership.domain.entities.Car;
import com.car.dealership.domain.entities.User;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {

    Optional<Car> findById(String id);

    Optional<Car> findAllByBrand(String brand);
}
