package com.car.dealership.service;

import java.util.List;
import java.util.Set;

import com.car.dealership.domain.dto.DealershipDTO;
import com.car.dealership.domain.entities.Car;
import com.car.dealership.domain.entities.Dealership;

public interface DealershipService {

    List<DealershipDTO> getAllDealerships();

    DealershipDTO addDealership(DealershipDTO dealership);

    DealershipDTO getById(String id);

    DealershipDTO updateDealership(DealershipDTO modifiedDealership);

    void deleteDealership(String id);
}
