package com.manisha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manisha.entity.UserAccountEntity;

public interface UserAccountRepo extends JpaRepository<UserAccountEntity, Integer> {
	public UserAccountEntity findByEmailAndPassword (String email,String pwd);
	
	public UserAccountEntity findByEmail(String emailId);
	

}
