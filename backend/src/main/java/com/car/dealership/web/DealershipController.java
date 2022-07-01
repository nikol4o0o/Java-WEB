package com.car.dealership.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.car.dealership.domain.dto.DealershipDTO;
import com.car.dealership.domain.entities.Car;
import com.car.dealership.domain.entities.Dealership;
import com.car.dealership.service.CarService;
import com.car.dealership.service.DealershipService;

@RestController
@RequestMapping("/api/dealerships")
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials="true", allowedHeaders = "*")
public class DealershipController {
    private final DealershipService dealershipService;

    @Autowired
    public DealershipController(DealershipService dealershipService) {
        this.dealershipService = dealershipService;
    }

    @GetMapping
    List<DealershipDTO> getAllDealerships() {
        return dealershipService.getAllDealerships();
    }

    @PostMapping
    DealershipDTO addDealership(@RequestBody DealershipDTO dealership) {
        return dealershipService.addDealership(dealership);
    }

    @GetMapping("/{id}")
    DealershipDTO getSingleDealership(@PathVariable String id) {
        return this.dealershipService.getById(id);
    }

    @PutMapping
    DealershipDTO modifyDealership(@RequestBody DealershipDTO modifiedDealership) {
        return this.dealershipService.updateDealership(modifiedDealership);
    }

    @DeleteMapping("/{id}")
    void deleteDealership(@PathVariable String id) {
        this.dealershipService.deleteDealership(id);
    }

    @GetMapping("/user/{id}")
    List<DealershipDTO> getAllDealershipsForUser(@PathVariable String id) {
        return dealershipService.getAllDealerships().stream().filter(x -> x.getUser().equals(id)).collect(Collectors.toUnmodifiableList());
    }

}
