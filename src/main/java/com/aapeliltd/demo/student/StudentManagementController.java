package com.aapeliltd.demo.student;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management/api/vi/students")
public class StudentManagementController {
	
	
	private List<Student> students = Arrays.asList( 
			
			new Student(1, "James Oladimeji"),
			new Student(2, "Juston Matt Coker"),
			new Student(3, "Shola Ogunniyi")
	);
	
	//protected our Api using permission
	//has_role(ROLE_) hasAnyRole(ROLE_) hasAuthority(permission), hasAnyAuthority(permission)
	
	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public void addStudent(@RequestBody Student student) {
		
		//this.students.add(student);
		//System.out.println("ADD: "+ student);
		System.out.println("Added");
	}
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRANEE')")
	public List<Student> getAllStudents() {
		return this.students;
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('student:write')")
	public void deleteStudent(@PathVariable("id") int studentid)
	{
		System.out.println("deleted");
		
	}
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('student:write')")
	public void updateStudent(@PathVariable("id") int id, @RequestBody Student student) {
		
		System.out.println("updated");
		
		
	}
	

}
