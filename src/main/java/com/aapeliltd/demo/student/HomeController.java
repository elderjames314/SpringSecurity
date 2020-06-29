package com.aapeliltd.demo.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	
	@GetMapping("/error")
	public String error() {
		
		return "Something went wrogn, please check back...";
	}

}
