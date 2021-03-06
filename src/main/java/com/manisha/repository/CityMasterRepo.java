package com.manisha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manisha.entity.CityMasterEntity;

public interface CityMasterRepo extends JpaRepository<CityMasterEntity, Integer> {
	
	public List<CityMasterEntity>  findByStateId(Integer stateId);

}
