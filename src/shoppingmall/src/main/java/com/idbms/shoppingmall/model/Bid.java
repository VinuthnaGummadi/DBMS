package com.idbms.shoppingmall.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.idbms.shoppingmall.util.SQLInjectionSafe;

@Entity
@Table(name = "BID")
public class Bid {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="BID_ID")
	private int bidID;
	
	@Column(name="START_DATE")
	@SQLInjectionSafe
	private String startDate;
	
	@Column(name="END_DATE")
	@SQLInjectionSafe
	private String endDate;
	
	@Column(name="AUTHORIZED")
	private boolean authorized;
	
	@Column(name="RENT")
	private double rent= 0;
	
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="shop_id",referencedColumnName="shop_id")
	private Shops shop;
	
	@Transient
	@SQLInjectionSafe
	private String floorLevel;
	
	@Transient
	@SQLInjectionSafe
	private String email;

	public int getBidID() {
		return bidID;
	}

	public void setBidID(int bidID) {
		this.bidID = bidID;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public Shops getShop() {
		return shop;
	}

	public void setShop(Shops shop) {
		this.shop = shop;
	}

	public String getFloorLevel() {
		return floorLevel;
	}

	public void setFloorLevel(String floorLevel) {
		this.floorLevel = floorLevel;
	}

	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
}
