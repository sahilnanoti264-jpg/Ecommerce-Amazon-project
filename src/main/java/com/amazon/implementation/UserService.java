package com.amazon.implementation;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amazon.dto.ChangePasswordRequest;
import com.amazon.dto.UpdateUserRequest;
import com.amazon.dto.UserRequest;
import com.amazon.dto.UserResponse;
import com.amazon.email.EmailService;
import com.amazon.email.EmailSubject;
import com.amazon.email.template.RegisterationEmailTemplate;
import com.amazon.entity.Address;
import com.amazon.entity.Cart;
import com.amazon.entity.User;
import com.amazon.enumeration.ProfileStatus;
import com.amazon.enumeration.Role;
import com.amazon.exception.EmailSendingException;
import com.amazon.exception.PasswordIncorrectException;
import com.amazon.exception.UserNotFoundException;
import com.amazon.repository.IUserRepository;
import com.amazon.service.IUserService;

import jakarta.mail.MessagingException;

@Service("user")
public class UserService implements IUserService{

	private final IUserRepository repository;
	private final RegisterationEmailTemplate template;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;

	public UserService(IUserRepository repository, RegisterationEmailTemplate template, 
			EmailService emailService,PasswordEncoder passwordEncoder) {
		this.repository=repository;
		this.template=template;
		this.emailService=emailService;
		this.passwordEncoder=passwordEncoder;
	}
	
	@Override
	public UserResponse registerUser(UserRequest request){
		if(request==null)  
			throw new IllegalArgumentException("Invalid user details");
		
		User user=new User(); 
		
		BeanUtils.copyProperties(request, user);
		
		user.setRole(Role.USER);
		user.setStatus(ProfileStatus.ACTIVE);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		Cart cart=new Cart();
		user.setCart(cart);
		cart.setUser(user);
		
		//To save the user into address table
		for (Address address : user.getAddress()) {
		    address.setUser(user);
		}
		
		User savedUser=repository.save(user);
		
		UserResponse response = new UserResponse();
		BeanUtils.copyProperties(savedUser, response);
		
		try {
			emailService.sentEmail(savedUser.getEmail(), EmailSubject.REGISTRATION, template.registration(user));

		}catch( MessagingException e) {
			e.printStackTrace();
			throw new EmailSendingException("Unable to send registration email", e);
		}
		return response;
	}
	
	@Override
	public UserResponse createAdmin(UserRequest request) throws IllegalArgumentException {
		if(request==null)  
			throw new IllegalArgumentException("Invalid user details");
		User admin=new User();
		BeanUtils.copyProperties(request, admin);
		admin.setRole(Role.ADMIN);
		admin.setStatus(ProfileStatus.ACTIVE);
		admin.setPassword(passwordEncoder.encode(request.getPassword()));
		Cart cart=new Cart();
		admin.setCart(cart);
		cart.setUser(admin);
		
		//To save the user into address table
		for (Address address : admin.getAddress()) {
		    address.setUser(admin);
		}
		
		User savedUser=repository.save(admin);
		UserResponse response = new UserResponse();
		BeanUtils.copyProperties(savedUser, response);
		
		return response;
	}

	@Override
	public UserResponse getUserById(Long userId) throws IllegalArgumentException {
		User savedUser=repository.findById(userId).orElseThrow(()->new IllegalArgumentException("Invalid Id"));
		UserResponse response=new UserResponse();
		BeanUtils.copyProperties(savedUser, response);
		return response;
	}

	@Override
	public List<UserResponse> getAllUsers() {

	    List<User> users = repository.findByRole(Role.USER);

	    return users.stream()
	            .map(user -> {
	                UserResponse response = new UserResponse();
	                BeanUtils.copyProperties(user, response);
	                return response;
	            })
	            .toList();
	}

	@Override
	public UserResponse updateUser(Long userId,UpdateUserRequest request) throws UserNotFoundException {
		if(userId==null || request==null) throw new IllegalArgumentException("Invalid user details");
		User user=repository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
		BeanUtils.copyProperties(request, user, "address"); 			//copy all fields except address field
		User savedUser = repository.save(user);
		UserResponse response=new UserResponse();
		BeanUtils.copyProperties(savedUser,response);
		return response;
	}

	@Override
	public String deleteUserById(Long userId) throws IllegalArgumentException, UserNotFoundException {
		if(userId==null) 
			throw new IllegalArgumentException("Invalid user Id");
		User user=repository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id= "+userId));
		repository.delete(user);
		return "User is deleted successfully.";
	}

	@Override
	public Boolean existByEmail(String email) throws IllegalArgumentException {
		if(email.isEmpty()) throw new IllegalArgumentException("Invalid Email.");
		User user=repository.existByEmail(email);
		return user!=null;
	}

	@Override
	public String modifyStatus(Long userId, ProfileStatus status) throws UserNotFoundException {

		if (userId == null || status == null)
		    throw new IllegalArgumentException("Invalid Details.");
    
		User user=repository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id= "+userId));
		
		user.setStatus(status);
		User savedUser=repository.save(user);
		return "Status is successfully modified with Id: "+savedUser.getUserId();
	}

	@Override
	public String changePassword(ChangePasswordRequest request) throws UserNotFoundException {
		if (request == null)
		    throw new IllegalArgumentException("Invalid Details.");
    
		User user=repository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found with id= "+request.getUserId()));
		
		boolean isMatch=passwordEncoder.matches(user.getPassword(), request.getOldPassword());
		
		if(!isMatch) 
			throw new PasswordIncorrectException("Password is incorrect");
		
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		
		User savedUser = repository.save(user);
		return "Password is changed with Id: "+savedUser.getUserId();
	}

	@Override
	public List<UserResponse> getAllAdmin() throws IllegalArgumentException {
		   List<User> users = repository.findByRole(Role.ADMIN);

		    return users.stream()
		            .map(user -> {
		                UserResponse response = new UserResponse();
		                BeanUtils.copyProperties(user, response);
		                return response;
		            })
		            .toList();
	}

}
