package com.car.dealership.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dealership.domain.entities.Dealership;
import com.car.dealership.repository.DealershipRepository;

@Service
public class DealershipServiceImpl implements DealershipService {

    private final DealershipRepository dealershipRepository;

    @Autowired
    public DealershipServiceImpl(DealershipRepository dealershipRepository) {
        this.dealershipRepository = dealershipRepository;
    }

    @Override
    public List<Dealership> getAllDealerships() {
        return this.dealershipRepository.findAll();
    }

    @Override
    public Dealership addDealership(Dealership dealership) {
        return this.dealershipRepository.saveAndFlush(dealership);
    }

    @Override
    public Dealership getById(String id) {
        return this.dealershipRepository.findById(id).get();
    }

    @Override
    public Dealership updateDealership(String id, Dealership modifiedDealership) {
        return this.dealershipRepository.findById(id)
                .map(dealership -> {
                    dealership.setCars(modifiedDealership.getCars());
                    dealership.setCompanyOwner(modifiedDealership.getCompanyOwner());
                    dealership.setName(modifiedDealership.getName());
                    dealership.setYearFounded(modifiedDealership.getYearFounded());
                    return this.dealershipRepository.save(dealership);
                })
                .orElseGet(() -> {
                    modifiedDealership.setId(id);
                    return this.dealershipRepository.save(modifiedDealership);
                });
    }

    @Override
    public void deleteDealership(String id) {
        this.dealershipRepository.deleteById(id);
    }

}
