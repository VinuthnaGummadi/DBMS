package com.idbms.shoppingmall.model;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.idbms.shoppingmall.util.SQLInjectionSafe;


@Entity
@Table(name = "FLOORS")
public class Floors {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="FLOOR_ID")
	private int floorID;
	
	@Column(name="FLOOR_LEVEL",length=100,nullable=false)
	@NotEmpty(message = "*Please provide your floor level")
	@SQLInjectionSafe
	private String floorLevel;
	
	@Column(name="FLOOR_PLAN",length=100,nullable=false)
	@NotEmpty(message = "*Please provide your floor plan")
	@SQLInjectionSafe
	private String floorPlan;
	
	@Column(name="FLOOR_CATEGORY",length=100,nullable=false)
	@NotEmpty(message = "*Please provide your floor category")
	@SQLInjectionSafe
	private String floorCategory;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="floor_id",referencedColumnName="floor_id")
	private Set<Shops> shops = new HashSet<Shops>();

	public int getFloorID() {
		return floorID;
	}

	public void setFloorID(int floorID) {
		this.floorID = floorID;
	}

	public String getFloorLevel() {
		return floorLevel;
	}

	public void setFloorLevel(String floorLevel) {
		this.floorLevel = floorLevel;
	}

	public String getFloorPlan() {
		return floorPlan;
	}

	public void setFloorPlan(String floorPlan) {
		this.floorPlan = floorPlan;
	}

	public String getFloorCategory() {
		return floorCategory;
	}

	public void setFloorCategory(String floorCategory) {
		this.floorCategory = floorCategory;
	}

	public Set<Shops> getShops() {
		return shops;
	}

	public void setShops(Set<Shops> shops) {
		this.shops = shops;
	}

	
	
	
	
}
