package com.address.ADDRESS.service;

import com.address.ADDRESS.model.dto.AddressDto;
import com.address.ADDRESS.model.dto.AddressRequest;
import com.address.ADDRESS.model.dto.AddressDto;

import java.util.List;

public interface AddressService {

    List<AddressDto> saveAddress(AddressRequest addressRequest);

    List<AddressDto> updateAddress(AddressRequest addressRequest);

    void deleteAddress(Long id);

    AddressDto getSingleAddress(Long id);

    List<AddressDto> getAllAddress();
}
