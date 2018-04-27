package com.idbms.shoppingmall.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SALES")
public class Sales {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="SALE_ID")
	private int saleID;
	
	@Column(name="SALE_NAME",length=1000)
	private String saleName;
	
	@Column(name="SALE_DESCRIPTION",length=1000)
	private String saleDescription;

	public int getSaleID() {
		return saleID;
	}

	public void setSaleID(int saleID) {
		this.saleID = saleID;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public String getSaleDescription() {
		return saleDescription;
	}

	public void setSaleDescription(String saleDescription) {
		this.saleDescription = saleDescription;
	}
	
	
	
}
