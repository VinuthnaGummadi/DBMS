package com.idbms.shoppingmall.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "SHOPS")
public class Shops {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="SHOP_ID")
	private int shopID;
	
	@Column(name="SHOP_NAME",length=100)
	@NotEmpty(message = "*Please provide your shop name")
	private String shopName;
	
	@Column(name="CATEGORY",length=100)
	@NotEmpty(message = "*Please provide your shop category")
	private String category;
	
	@Column(name="SHOP_OCCUPIED")
	private boolean shopOccupied=false;
	
	@Column(name="MIN_RENT")
	private double minRent;
	
	@Column(name="RENT")
	private double rent;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="SHOP_NUMBER")
	private String shopNumber;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="shop_id",referencedColumnName="shop_id")
	private Set<Sales> sales = new HashSet<Sales>();
	
	@Column(name="FLOOR_ID")
	private int floorID;
	
	//Only for input form
	@Transient
	private String floorLevel;

	public int getShopID() {
		return shopID;
	}

	public void setShopID(int shopID) {
		this.shopID = shopID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isShopOccupied() {
		return shopOccupied;
	}

	public void setShopOccupied(boolean shopOccupied) {
		this.shopOccupied = shopOccupied;
	}

	public double getMinRent() {
		return minRent;
	}

	public void setMinRent(double minRent) {
		this.minRent = minRent;
	}

	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getFloorID() {
		return floorID;
	}

	public void setFloorID(int floorID) {
		this.floorID = floorID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShopNumber() {
		return shopNumber;
	}

	public void setShopNumber(String shopNumber) {
		this.shopNumber = shopNumber;
	}

	public Set<Sales> getSales() {
		return sales;
	}

	public void setSales(Set<Sales> sales) {
		this.sales = sales;
	}

	public String getFloorLevel() {
		return floorLevel;
	}

	public void setFloorLevel(String floorLevel) {
		this.floorLevel = floorLevel;
	}
	
	
	
}
