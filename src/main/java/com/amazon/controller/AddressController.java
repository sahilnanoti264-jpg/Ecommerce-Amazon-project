package com.amazon.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.dto.AddressRequest;
import com.amazon.dto.AddressResponse;
import com.amazon.exception.AddressNotFoundException;
import com.amazon.exception.UserNotFoundException;
import com.amazon.service.IAddressService;

@RestController
@RequestMapping("address")
public class AddressController {
	
	private final IAddressService addressService;
	
	public AddressController(IAddressService addressService) {
		this.addressService=addressService;
	}
	
	@PostMapping("/add/{userId}")
	public ResponseEntity<String> addAddress(@PathVariable Long userId, @RequestBody AddressRequest addressDTO) {
		addressService.addAddress(userId, addressDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body("Address is successfully inserted.");
	}
	
	@GetMapping("/{addressId}")
	public ResponseEntity<AddressResponse> getAddress(@PathVariable Long addressId) {
		return ResponseEntity.ok(addressService.getAddressById(addressId));
	}
	
	@GetMapping("/addressList/{userId}")
	public ResponseEntity<List<AddressResponse>> getAllAddressesByUserId(@PathVariable Long userId){
		return ResponseEntity.ok(addressService.getAllAddressesByUserId(userId));
	}
	
	@PutMapping("/update/{addressId}")
	public ResponseEntity<String> updateAddress(@PathVariable Long addressId, @RequestBody AddressRequest addressDTO){
		addressService.updateAddress(addressId, addressDTO);
		return ResponseEntity.ok("Address is successfully updated.");
	}
	
	@DeleteMapping("/delete/{addressId}")
	public ResponseEntity<String> deleteAddress(@PathVariable Long addressId){
		return ResponseEntity.ok(addressService.deleteAddress(addressId));
	}
}
