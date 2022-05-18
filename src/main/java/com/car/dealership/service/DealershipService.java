package com.car.dealership.service;

import java.util.List;
import java.util.Set;

import com.car.dealership.domain.entities.Car;
import com.car.dealership.domain.entities.Dealership;

public interface DealershipService {

    List<Dealership> getAllDealerships();

    Dealership addDealership(Dealership dealership);

    Dealership getById(String id);

    Dealership updateDealership(String id, Dealership modifiedDealership);

    void deleteDealership(String id);
}
