package com.amazon.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	private final String SECRET_KEY="my-super-secret-key-my-super-secret-key";
	
	public String generateToken(UserDetails userDetails) {
		
		return Jwts.builder()
				.subject(userDetails.getUsername())
				.claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(getSigningKey())
				.compact();
	}
	
	public SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}
	
	public String extractUserName(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload().getSubject();
				
	}
	
	public boolean isTokenValid(String token , UserDetails userDetails) {
		String userName=extractUserName(token);
		return userName.equals(userDetails.getUsername());
	}
	
}
