package com.address.ADDRESS.service.impl;

import com.address.ADDRESS.exception.BadRequestException;
import com.address.ADDRESS.exception.ResourceNotFoundException;
import com.address.ADDRESS.model.dto.AddressDto;
import com.address.ADDRESS.model.dto.AddressRequest;
import com.address.ADDRESS.model.dto.AddressRequestDto;
import com.address.ADDRESS.model.entity.Address;
import com.address.ADDRESS.repository.AddressRepository;
import com.address.ADDRESS.service.AddressService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class  AddressServiceImpl implements AddressService {

    Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    public final AddressRepository addressRepository;
    public final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public AddressDto getSingleAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found with Id : " + id));
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAllAddress() {
        List<Address> allAddress = addressRepository.findAll();
        if (allAddress.isEmpty()) {
            throw new RuntimeException("No address found");
        }
        return allAddress.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found with Id : " + id));
        addressRepository.delete(address);
    }

    @Override
    public List<AddressDto> saveAddress(AddressRequest addressRequest) {
        //Todo check if employee exists

        List<Address> listToSave = this.saveOrUpdateAddress(addressRequest);
        List<Address> savedAddress = addressRepository.saveAll(listToSave);
        return savedAddress.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public List<AddressDto> updateAddress(AddressRequest addressRequest) {
        //Todo check if employee exists

        List<Address> addressByEmpId = addressRepository.findAllByEmpId(addressRequest.getEmpId());
        if(addressByEmpId.isEmpty()){
            log.info("No address found for employee Id : {}",addressRequest.getEmpId());
            log.info("Creating new address for employee Id : {}", addressRequest.getEmpId());
        }

        List<Address> listToUpdate = this.saveOrUpdateAddress(addressRequest);

        List<Long> upcomingNonNullIds = listToUpdate.stream().map(Address::getId).filter(Objects::nonNull).toList();
        List<Long> existingIds = addressByEmpId.stream().map(Address::getId).toList();

        List<Long> idsToDelete = existingIds.stream().filter(id -> !upcomingNonNullIds.contains(id)).toList();
        if (!idsToDelete.isEmpty()){
            addressRepository.deleteAllById(idsToDelete);
        }

        List<Address> updatedAddress = addressRepository.saveAll(listToUpdate);
        return updatedAddress.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }


    private List<Address> saveOrUpdateAddress(AddressRequest addressRequest) {
        List<Address> listToSave = new ArrayList<>();
        for (AddressRequestDto addressRequestDto : addressRequest.getAddressRequestDtoList()) {
            Address address = new Address();
            address.setId(addressRequestDto.getId()!= null ? addressRequestDto.getId() : null);
            address.setStreet(addressRequestDto.getStreet());
            address.setCity(addressRequestDto.getCity());
            address.setCountry(addressRequestDto.getCountry());
            address.setPinCode(addressRequestDto.getPinCode());
            address.setAddressType(addressRequestDto.getAddressType());
            address.setEmpId(addressRequest.getEmpId());
            listToSave.add(address);
        }
        return listToSave;
    }
}
