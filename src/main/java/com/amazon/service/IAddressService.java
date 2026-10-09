package com.amazon.service;

import java.util.List;

import com.amazon.dto.AddressRequest;
import com.amazon.dto.AddressResponse;
import com.amazon.exception.AddressNotFoundException;
import com.amazon.exception.UserNotFoundException;

public interface IAddressService {

    public AddressResponse addAddress(Long userId, AddressRequest addressDTO)
            throws UserNotFoundException;

    public AddressResponse getAddressById(Long addressId)
            throws AddressNotFoundException;

    public List<AddressResponse> getAllAddressesByUserId(Long userId)
            throws UserNotFoundException;

    public AddressResponse updateAddress(Long addressId,AddressRequest addressDTO) throws AddressNotFoundException;

    public String deleteAddress(Long addressId)
            throws AddressNotFoundException;
}
