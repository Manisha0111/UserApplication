package com.manisha.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.manisha.bindings.LoginForm;
import com.manisha.service.UserMgmtService;

@RestController
public class LoginRestController {
	@Autowired
	private UserMgmtService service;

	@PostMapping("/login")
	public String login(@RequestBody LoginForm form) {

		return service.logIn(form);
	}
}
