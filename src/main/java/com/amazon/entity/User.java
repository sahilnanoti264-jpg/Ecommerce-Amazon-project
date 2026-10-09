package com.amazon.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.amazon.enumeration.ProfileStatus;
import com.amazon.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="user")
@Data
@Table(name="amazon_user")
@NoArgsConstructor
public class User {
	@Id
	@SequenceGenerator(name="user_seq",initialValue = 1)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	
	private String userName;
	
	private String firstName;
	
	private String lastName;

	private String phoneNumber;

	private String email;
	
    private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Enumerated(EnumType.STRING)
	private ProfileStatus status;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("user-order")
	@ToString.Exclude
	private List<Order> order=new ArrayList<>();
	
	@OneToOne(mappedBy="user", cascade=CascadeType.ALL,orphanRemoval = true)
	@JsonManagedReference("user-cart")
	@ToString.Exclude
	private Cart cart;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	@JsonManagedReference("user-address")      //Include JSON
	@ToString.Exclude
	private List<Address> address=new ArrayList<>();
	
	
	//MetaData
	@Version
	private Integer version;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updateAt;
	
}
