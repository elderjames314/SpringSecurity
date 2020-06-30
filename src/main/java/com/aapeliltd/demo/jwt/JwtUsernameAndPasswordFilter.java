package com.aapeliltd.demo.jwt;

import java.io.IOException;
import java.security.Key;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUsernameAndPasswordFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtConfig jwtConfig;
	
	
	
	public JwtUsernameAndPasswordFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}



	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			UsernameAndPasswordRequest authenticationRequest =
					new ObjectMapper().readValue(request.getInputStream(),
							UsernameAndPasswordRequest.class);
			
			//check the user identity
			Authentication authentication  = new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword());
			
			//this authentication manager will if username does exist and password is correct, if yest, it will authenticate the user.
			Authentication authenticate = authenticationManager.authenticate(authentication);
			
			return authenticate;
			
		}catch (java.io.IOException e) {
			throw new RuntimeException(e);
		}
	
		
	}



	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String secretKey = "JavaIsMyFirstLoveJavaIsMyFirstLoveJavaIsMyFirstLoveJavaIsMyFirstLoveJavaIsMyFirstLove";
		String token = Jwts.builder().setSubject(authResult.getName())
					.claim("authorities", authResult.getAuthorities())
					.setIssuedAt(new Date())
					.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
					.signWith(Keys
							.hmacShaKeyFor(secretKey.getBytes())).compact();
		
		response.addHeader("Authorization", "Bearer "+ token);
	}
	
	
	
	

}
