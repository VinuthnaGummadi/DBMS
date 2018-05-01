package com.idbms.shoppingmall.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.idbms.shoppingmall.util.SQLInjectionSafe;

@Entity
@Table(name = "MALL")
public class Mall {
	
	@Id
	@Column(name="MALL_ID")
	private int mallId;
	
	@Column(name="MALL_NAME",nullable=false,length=20)
	@SQLInjectionSafe
	private String mallName;
	
	@Column(name="ADDRESS",nullable=false,length=100)
	@SQLInjectionSafe
	private String address;
	
	@Column(name="PHONE_NO",length=10,nullable=false)
	@Size(min=10,max=10)
	@Pattern(regexp="(^$|[0-9]{10})")
	@SQLInjectionSafe
	private String phoneNo;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="mall_id",referencedColumnName="mall_id")
	private List<Floors> floors = new ArrayList<Floors>();

	public int getMallId() {
		return 1;
	}

	public void setMallId(int mallId) {
		this.mallId = 1;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public List<Floors> getFloors() {
		return floors;
	}

	public void setFloors(List<Floors> floors) {
		this.floors = floors;
	}
	
	
	
}
