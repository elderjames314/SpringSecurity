package com.aapeliltd.demo.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum ApplicationUserRole {
	
	ADMIN (Sets.newHashSet(
			ApplicationUserPermission.COURSE_WRITE,
			ApplicationUserPermission.COURSE_READ,
			ApplicationUserPermission.STUDENT_READ,
			ApplicationUserPermission.STUDENT_WRITE)),
	TRANEE (Sets.newHashSet(
			ApplicationUserPermission.COURSE_READ,
			ApplicationUserPermission.STUDENT_READ
			)),
	STUDENT(Sets.newHashSet()); //student can have zero or no permission
	
	private final Set<ApplicationUserPermission> permission;
	
	ApplicationUserRole(Set<ApplicationUserPermission> permission) {
		this.permission = permission;
	}
	
	public Set<ApplicationUserPermission> getPermission() {
		return this.permission;
	}
	
	public Set<SimpleGrantedAuthority> getGrandtedAuthorities() {
		Set<SimpleGrantedAuthority> permissions = getPermission().stream()
			.map(permission->new SimpleGrantedAuthority(permission.getPermission()))
			.collect(Collectors.toSet());
		
		permissions.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
		
		return permissions;
	}
	
	

}
