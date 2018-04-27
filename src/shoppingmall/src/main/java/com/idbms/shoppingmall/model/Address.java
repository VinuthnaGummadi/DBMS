package com.idbms.shoppingmall.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "USER_ADDRESS")
public class Address {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int addressID;
	
	@Column(name="STREET",length=100,nullable=false)
	@Length(max=20, message = "*Your Street should be of maximum 20 characters")
	@NotEmpty(message = "*Please provide your Street")
	private String street;
	
	@Column(name="CITY",length=100,nullable=false)
	@Length(max=20, message = "*Your City should be of maximum 20 characters")
	@NotEmpty(message = "*Please provide your CITY")
	private String city;
	
	@Column(name="STATE",length=100,nullable=false)
	@Length(max=20, message = "*Your State should be of maximum 20 characters")
	@NotEmpty(message = "*Please provide your STATE")
	private String state;
	
	@Column(name="COUNTRY",length=100,nullable=false)
	@Length(max=20, message = "*Your Country should be of maximum 20 characters")
	@NotEmpty(message = "*Please provide your COUNTRY")
	private String country;
	
	@Column(name="PIN",length=5,nullable=false)
	@Length(min = 5,max=5, message = "*Your pin must be 5 digits")
	@NotEmpty(message = "*Please provide your PIN")
	private String pin;

	public int getAddressID() {
		return addressID;
	}

	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	
}
