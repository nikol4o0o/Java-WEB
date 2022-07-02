package com.car.dealership.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.dealership.domain.entities.Car;
import com.car.dealership.domain.entities.Dealership;

@Repository
public interface DealershipRepository extends JpaRepository<Dealership, String> {

}
