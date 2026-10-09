package com.amazon.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name="address")
@Getter
@Setter
@Table(name="amazon_user_address")
@NoArgsConstructor
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
	@ManyToOne
	@JsonBackReference("user-address")      //JSON save from recursion--->because address has user so that it's following recursion going back to back continuous 
	@JoinColumn(name="user_id")
	private User user;
	private Integer plotNo;
	private String street;
	private String city;
	private String state;
	private Long pincode;
	private String country;
}
