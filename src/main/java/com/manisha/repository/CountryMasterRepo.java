package com.manisha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manisha.entity.CountryMasterEntity;

public interface CountryMasterRepo extends JpaRepository<CountryMasterEntity, Integer> {

}
