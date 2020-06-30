package com.aapeliltd.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {
	
	
	@GetMapping("login")
	public String getLogin() {
		return "login1.html";
	}
	
	@GetMapping("courses")
	public String getCourses() {
		return "course.html";
	}
	
	@GetMapping("error")
	public String getError() {
		return "error.html";
	}
	
	

}
