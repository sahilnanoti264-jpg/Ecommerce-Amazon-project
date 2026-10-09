package com.amazon.implementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.amazon.entity.User;
import com.amazon.repository.IUserRepository;
/*
 * CustomUserDetailsService class acts as bridge between database and class.
 * It tells to Spring Security you want any information regard user let me know it will provide. 
 */

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	private final IUserRepository userRepository;
	
	public CustomUserDetailsService(IUserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByUserName(username).orElseThrow(() -> 
				  new UsernameNotFoundException("Username not found"));
		return org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
				.password(user.getPassword()) 
				.roles(user.getRole().toString())
				.build();
	}

}
