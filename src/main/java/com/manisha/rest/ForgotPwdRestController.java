package com.manisha.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.manisha.service.UserMgmtService;

@RestController
public class ForgotPwdRestController {

	@Autowired
	private UserMgmtService service;

	@GetMapping("/forgotPassword/{email}")
	public String forgotPassword(@PathVariable String email) {
		return service.forgotPassword(email);

	}

}
