package com.manisha.service;

import java.util.Map;

import com.manisha.bindings.LoginForm;
import com.manisha.bindings.UnlockForm;
import com.manisha.bindings.UserForm;

public interface UserMgmtService {
	
	public String registerUser (UserForm userform); 
	public String emailcheck(String emailId);
	public String logIn (LoginForm user); 
	public String forgotPassword (String email); 
	public String unlockAccount (UnlockForm unlockAccForm);
	public Map<Integer,String> getcountry();
	public Map<Integer,String> getState(Integer countryId);
	public Map<Integer,String> getCity(Integer stateId);


}
