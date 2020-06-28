package com.aapeliltd.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
			.antMatchers("/", "/index", "/error", "/css/*", "/jss/*")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}

	//to create user, you will need override this.
	//ideally you should retrieve the user from the database
	//but for the example sake, lets build the user and use it.
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		UserDetails jamesUser = User.builder()
				.username("james")
				.password("123456")
				.roles("STUDENT") // ROLE_STUDENT
				.build();
		
		return new InMemoryUserDetailsManager(
				jamesUser
		);
	  
	
		
	}
	
	
	
	
	


	
	
	
	

}
