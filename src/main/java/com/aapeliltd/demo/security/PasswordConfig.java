package com.aapeliltd.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		//add the strength of 10.
		return new BCryptPasswordEncoder(10);
		
	}

}
