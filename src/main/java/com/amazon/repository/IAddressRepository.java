package com.amazon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amazon.dto.AddressResponse;
import com.amazon.entity.Address;

public interface IAddressRepository extends JpaRepository<Address, Long> {
	
	@Query("SELECT a FROM address a WHERE a.user.userId = :userId")
	 public List<Address> findByUserId(Long userId);
}
