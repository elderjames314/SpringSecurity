package com.aapeliltd.demo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/students")
public class StudentController {
	
	private final static List<Student> STUDENTS = Arrays.asList(
			new Student(1, "James Bond"),
			new Student(2, "Ana Smith"),
			new Student(3, "Chuck Noris")
			);
	
	@GetMapping("/{studentId}")
	public Student getStudent(@PathVariable("studentId") Integer studentId) {
		
		return STUDENTS.stream().filter(student->student.getStudentId().equals(studentId))
				.findFirst().orElseThrow(()-> new IllegalStateException("Student of ID: "+ studentId+" not found."));
		
	
		
	}

}
