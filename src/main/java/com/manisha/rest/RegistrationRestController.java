package com.manisha.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.manisha.bindings.UserForm;
import com.manisha.service.UserMgmtService;

@RestController
public class RegistrationRestController {

	@Autowired
	private UserMgmtService service;

	@GetMapping("/email/{emailID}")
	public String email(@PathVariable("emailID") String emailID) {

		return service.emailcheck(emailID);
	}

	@GetMapping("/countries")
	public Map<Integer, String> getcountry() {

		return service.getcountry();
	}

	@GetMapping("/states/{countryId}")
	public Map<Integer, String> getstate(@PathVariable("countryId") Integer countryId) {

		return service.getState(countryId);
	}

	@GetMapping("/city/{stateId}")
	public Map<Integer, String> getcity(@PathVariable("stateId") Integer stateId) {

		return service.getCity(stateId);
	}

	@PostMapping("/user")
	public String registerUser(@RequestBody UserForm userform) {

		return service.registerUser(userform);
	}
}
