package com.manisha.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.manisha.bindings.UnlockForm;
import com.manisha.service.UserMgmtService;

@RestController
public class UnlockAccRestController {

	@Autowired
	private UserMgmtService service;
	
	@PostMapping("/unlock")
	public String unlockAccount(@RequestBody UnlockForm unlockAccForm ) {

		return service.unlockAccount(unlockAccForm);
	}
}
