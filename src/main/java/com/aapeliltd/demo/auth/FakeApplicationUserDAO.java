package com.aapeliltd.demo.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.aapeliltd.demo.security.ApplicationUserRole;
import com.google.common.collect.Lists;

@Repository("fake")
public class FakeApplicationUserDAO implements ApplicationUserDAO {
	
	private final PasswordEncoder passwordEncoder;
	
	
	@Autowired
	public FakeApplicationUserDAO(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		return getApplicationUser()
				.stream()
				.filter(appUser->appUser.getUsername().equals(username))
				.findFirst();
	}
	
	private List<ApplicationUser> getApplicationUser() {
		
		//create in memory users
		List<ApplicationUser> applicationUsers = Lists.newArrayList(
				
				new ApplicationUser(
						ApplicationUserRole.STUDENT.getGrandtedAuthorities(),
						passwordEncoder.encode("123456"), 
						"tom", 
						true, 
						true, 
						true, 
						true
						),
				new ApplicationUser(
						ApplicationUserRole.ADMIN.getGrandtedAuthorities(),
						passwordEncoder.encode("123456"), 
						"max", 
						true, 
						true, 
						true, 
						true
						),
				new ApplicationUser(
						ApplicationUserRole.TRANEE.getGrandtedAuthorities(),
						passwordEncoder.encode("123456"), 
						"james", 
						true, 
						true, 
						true, 
						true
						)
				
				);
		return applicationUsers;
		
	}

}
