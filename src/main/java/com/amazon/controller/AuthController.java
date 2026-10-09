package com.amazon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.dto.LoginRequest;
import com.amazon.dto.LoginResponse;
import com.amazon.implementation.CustomUserDetailsService;
import com.amazon.util.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	
	private final CustomUserDetailsService customUserDetailsService;
	
	private final JwtService jwtService;

	public AuthController(AuthenticationManager authenticationManager,
			CustomUserDetailsService customUserDetailsService, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.customUserDetailsService = customUserDetailsService;
		this.jwtService = jwtService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request){
		
		try {
	        authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
	        );
	    } catch (org.springframework.security.core.AuthenticationException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	    }
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassword()));
		
		UserDetails userDetails= customUserDetailsService.loadUserByUsername(request.getUserName());
		
		String token=jwtService.generateToken(userDetails);
		
		return ResponseEntity.ok(new LoginResponse(token));
	}
	
}
