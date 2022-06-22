package com.car.dealership.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dealership.domain.dto.DealershipDTO;
import com.car.dealership.domain.entities.Car;
import com.car.dealership.domain.entities.Dealership;
import com.car.dealership.repository.DealershipRepository;

@Service
public class DealershipServiceImpl implements DealershipService {

    private final DealershipRepository dealershipRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public DealershipServiceImpl(DealershipRepository dealershipRepository, ModelMapper modelMapper,
            UserService userService) {
        this.dealershipRepository = dealershipRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public List<DealershipDTO> getAllDealerships() {
        return this.dealershipRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public DealershipDTO addDealership(DealershipDTO dealership) {
        Dealership dealershipEntity = convertToEntity(dealership);
        this.dealershipRepository.saveAndFlush(dealershipEntity);
        return dealership;
    }

    @Override
    public DealershipDTO getById(String id) {
        Optional<Dealership> dealership = this.dealershipRepository.findById(id);
        if (dealership.isEmpty()) {
            throw new NullPointerException("Dealership not found!");
        }
        return convertToDto(dealership.get());
    }

    @Override
    public DealershipDTO updateDealership(String id, DealershipDTO modifiedDealership) {
        return convertToDto(this.dealershipRepository.findById(id)
                .map(dealership -> {
                    dealership.setCars(modifiedDealership.getCars());
                    dealership.setCompanyOwner(modifiedDealership.getCompanyOwner());
                    dealership.setName(modifiedDealership.getName());
                    dealership.setYearFounded(modifiedDealership.getYearFounded());
                    return this.dealershipRepository.save(dealership);
                })
                .orElseGet(() -> {
                    modifiedDealership.setId(id);
                    Dealership dealershipEntity = convertToEntity(modifiedDealership);
                    this.dealershipRepository.save(dealershipEntity);
                    return dealershipEntity;
                }));
    }

    @Override
    public void deleteDealership(String id) {
        this.dealershipRepository.deleteById(id);
    }

    private DealershipDTO convertToDto(Dealership dealership) {
        DealershipDTO dealershipDTO = modelMapper.map(dealership, DealershipDTO.class);
        dealershipDTO.setUser(dealership.getUser().getId());
        return dealershipDTO;
    }

    private Dealership convertToEntity(DealershipDTO dealershipDto) {
        Dealership dealership = modelMapper.map(dealershipDto, Dealership.class);
        String userId = dealershipDto.getUser();
        if (userId == null || userService.findById(userId) == null) {
            throw new NullPointerException("No user found!");
        }
        dealership.setUser(userService.findById(userId));
        return dealership;
    }
}
