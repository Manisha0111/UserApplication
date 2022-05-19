package com.manisha.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Table(name = "USER_ACCOUNT")
@Data
public class UserAccountEntity {
	
	
	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private Integer userid;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "USER_EMAIL", unique=true)
	private String email;
	@Column(name = "USER_PWD")
	private String password;
	@Column(name = "USER_MOBILE")
	private Long phno;
	@Column(name = "DOB")
	private Date dob;
	@Column(name = "GENDER")
	private String gender;
	@Column(name = "COUNTRY_ID")
	private Integer countryId;
	@Column(name = "STATE_ID")
	private Integer stateId;
	@Column(name = "CITY_ID")
	private Integer cityId;
	@Column(name = "ACC_STATUS")
	private String accStatus;
	@Column(name = "CREATED_DATE",updatable= false)
	@CreationTimestamp
	private LocalDate createdDate;
	@Column(name = "UPDATED_DATE",insertable=false)
	@CreationTimestamp
	private LocalDate updateddDate;
	

}
