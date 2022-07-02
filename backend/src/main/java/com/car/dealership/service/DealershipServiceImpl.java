package com.car.dealership.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dealership.domain.dto.DealershipDTO;
import com.car.dealership.domain.entities.Dealership;
import com.car.dealership.repository.DealershipRepository;
import com.car.dealership.repository.UserRepository;

@Service
public class DealershipServiceImpl implements DealershipService {

    private final DealershipRepository dealershipRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DealershipServiceImpl(DealershipRepository dealershipRepository, UserRepository userRepository,
            ModelMapper modelMapper) {
        this.dealershipRepository = dealershipRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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
    public DealershipDTO updateDealership(DealershipDTO modifiedDealership) {
        String dealershipId = modifiedDealership.getId();
        Optional<Dealership> dealershipOptional = this.dealershipRepository.findById(dealershipId);
        if(dealershipOptional.isEmpty()){
            throw new NullPointerException("Dealership not found!");
        }
        Dealership dealershipFromDb = dealershipOptional.get();
        dealershipFromDb.setCompanyOwner(modifiedDealership.getCompanyOwner());
        dealershipFromDb.setName(modifiedDealership.getName());
        dealershipFromDb.setYearFounded(modifiedDealership.getYearFounded());

        return convertToDto(this.dealershipRepository.save(dealershipFromDb));
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
        if (userId == null || !userRepository.findById(userId).isPresent()) {
            throw new NullPointerException("No user found!");
        }
        dealership.setUser(userRepository.findById(userId).get());
        return dealership;
    }
}
