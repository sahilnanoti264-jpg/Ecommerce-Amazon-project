package com.amazon.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.amazon.util.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;
	
	private final UserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService){
		this.jwtService=jwtService;
		this.userDetailsService=userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		
		String token = null;
		String userName = null;
		
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			
			token= authHeader.substring(7);
			userName=jwtService.extractUserName(token);
		}
		
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails= userDetailsService.loadUserByUsername(userName);
			
			if(jwtService.isTokenValid(token, userDetails)) {
				
				//Creating object of authentication for spring security.
				UsernamePasswordAuthenticationToken authenticate=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				//Adding request details such as IP Address , session Id , etc
				authenticate.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// Saving Authentication
				SecurityContextHolder.getContext().setAuthentication(authenticate);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
