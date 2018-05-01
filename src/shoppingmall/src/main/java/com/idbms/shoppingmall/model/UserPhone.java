package com.idbms.shoppingmall.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.idbms.shoppingmall.util.SQLInjectionSafe;

@Entity
@Table(name = "USER_PHONE")
public class UserPhone {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PHONE_ID")
	private int phoneID;
	
	@Column(name="PHONE_NO",length=10,nullable=false)
	@Length(min = 10,max=10, message = "*Your phone number must be 10 digits")
	@NotEmpty(message = "*Please provide your Phone Number")
	@Pattern(regexp="(^$|[0-9]{10})")
	@SQLInjectionSafe
	private String phoneNo;

	public int getPhoneID() {
		return phoneID;
	}

	public void setPhoneID(int phoneID) {
		this.phoneID = phoneID;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	
}
