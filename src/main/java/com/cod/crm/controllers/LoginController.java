package com.cod.crm.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	@RequestMapping(value="/api/login/test",method=RequestMethod.GET)
	public String test() {
		return "hello";
		
	}

}
