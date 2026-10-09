package com.amazon.implementation;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.amazon.dto.AddressRequest;
import com.amazon.dto.AddressResponse;
import com.amazon.entity.Address;
import com.amazon.entity.User;
import com.amazon.exception.AddressNotFoundException;
import com.amazon.exception.UserNotFoundException;
import com.amazon.repository.IAddressRepository;
import com.amazon.repository.IUserRepository;
import com.amazon.service.IAddressService;

@Service("address")
public class AddressService implements IAddressService{
	
	private final IAddressRepository addressRepository;
	private final IUserRepository userRepository;
	
	public AddressService(IAddressRepository addressRepository, IUserRepository userRepository) {
		this.addressRepository=addressRepository;
		this.userRepository=userRepository;
	}
	
	@Override
	public AddressResponse updateAddress(Long addressId, AddressRequest request) throws AddressNotFoundException {

	    if (addressId == null || request == null) {
	        throw new IllegalArgumentException("Invalid address details");
	    }
	    Address existingAddress = addressRepository.findById(addressId)
	            .orElseThrow(() -> new AddressNotFoundException("Address not found"));

	    existingAddress.setPlotNo(request.getPlotNo());
	    existingAddress.setStreet(request.getStreet());
	    existingAddress.setCity(request.getCity());
	    existingAddress.setState(request.getState());
	    existingAddress.setPincode(request.getPincode());
	    
	    Address savedAddress=addressRepository.save(existingAddress);
	    
	    AddressResponse response=new AddressResponse();
	    BeanUtils.copyProperties(savedAddress,response);
	    
	    return response;
	}

	@Override
	public AddressResponse addAddress(Long userId, AddressRequest request) throws UserNotFoundException {
		if(userId==null || request==null)
			throw new IllegalArgumentException("Invalid address details");
		
		Address address=new Address();
		
		BeanUtils.copyProperties(request, address);
		
		User user=userRepository.findById(userId).orElseThrow( () -> 
					new UserNotFoundException("User not found"));

		address.setUser(user);
		
		Address savedAddress=addressRepository.save(address);
		
		AddressResponse addressResponse =new AddressResponse();
		
		BeanUtils.copyProperties(savedAddress, addressResponse);
		
		return addressResponse;
	}

	@Override
	public AddressResponse getAddressById(Long addressId) throws AddressNotFoundException {
		if(addressId==null) 
			throw new IllegalArgumentException("Invalid address id");
		Address savedAddress=addressRepository.findById(addressId).orElseThrow(() -> 
		new AddressNotFoundException("Address not found"));
		
		AddressResponse response =new AddressResponse();
		
		BeanUtils.copyProperties(savedAddress, response);
		
		return response;
	}

	@Override
	public List<AddressResponse> getAllAddressesByUserId(Long userId) throws UserNotFoundException {
		if(userId==null) 
			throw new IllegalArgumentException("Invalid user id");
		List<Address> address=addressRepository.findByUserId(userId);
		
		List<AddressResponse> responsList=address.stream().map( adrs ->{
			AddressResponse response=new AddressResponse(adrs.getAddressId(),adrs.getUser(), adrs.getPlotNo(),
					adrs.getStreet(),adrs.getCity(),adrs.getState(),adrs.getPincode(),adrs.getCountry());
			return response;
		}).toList();
		return responsList;
	}

	@Override
	public String deleteAddress(Long addressId) throws AddressNotFoundException {
		if(addressId==null) 
			throw new IllegalArgumentException("Invalid address id");
		addressRepository.deleteById(addressId);
		return "Address is successfully deleted.";
	}

}
