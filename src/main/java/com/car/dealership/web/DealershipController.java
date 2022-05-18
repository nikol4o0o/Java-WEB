package com.car.dealership.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.dealership.domain.entities.Car;
import com.car.dealership.domain.entities.Dealership;
import com.car.dealership.service.CarService;
import com.car.dealership.service.DealershipService;

@RestController
@RequestMapping("dealerships")
public class DealershipController extends BaseController {
    private final DealershipService dealershipService;

    @Autowired
    public DealershipController(DealershipService dealershipService) {
        this.dealershipService = dealershipService;
    }

    @GetMapping
    List<Dealership> getAllDealerships() {
        return dealershipService.getAllDealerships();
    }

    @PostMapping
    Dealership addDealership(@RequestBody Dealership dealership) {
        return dealershipService.addDealership(dealership);
    }

    @GetMapping("/{id}")
    Dealership getSingleDealership(@PathVariable String id) {
        return this.dealershipService.getById(id);
    }

    @PutMapping("/{id}")
    Dealership modifyDealership(@RequestBody Dealership modifiedDealership, @PathVariable String id) {
        return this.dealershipService.updateDealership(id, modifiedDealership);
    }

    @DeleteMapping("/{id}")
    void deleteDealership(@PathVariable String id) {
        this.dealershipService.deleteDealership(id);
    }

}
