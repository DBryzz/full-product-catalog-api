package com.bryzz.fullcatalog.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
@Entity
@Table(name = "product_tbl")
public class Product {

	@Id
	@GeneratedValue
	private int pxtId;
	private String pxtName;
	private int quantity;
	private long price;
	private String pxtUri;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Category category;
	
	
	// Constuctors
	public Product() {
		
	}
	
	
	public Product( String pxtName, int quantity, long price, String pxtUri) {
		super();
		this.pxtName = pxtName;
		this.quantity = quantity;
		this.price = price;
		this.pxtUri = pxtUri;
		}
	
	
	public Product(int pxtId, String pxtName, int quantity, long price, String pxtUri) {
		super();
		this.pxtId = pxtId;
		this.pxtName = pxtName;
		this.quantity = quantity;
		this.price = price;
		this.pxtUri = pxtUri;
		}
	
	public String getPxtUri() {
		return pxtUri;
	}

	public void setPxtUri(String pxtUri) {
		this.pxtUri = pxtUri;
	}

	// Getters and Setters
	public int getPxtId() {
		return pxtId;
	}
	public void setPxtId(int pxtId) {
		this.pxtId = pxtId;
	}
	public String getPxtName() {
		return pxtName;
	}
	public void setPxtName(String pxtName) {
		this.pxtName = pxtName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
	
	
	public Category getCategory() {
		return new Category(getCatId(), getCatName());
	}
	
	@JsonIgnore
	public String getCatName() {
		return category.getCatName();
	}
	
	@JsonIgnore
	public int getCatId() {
		return category.getCatId();
	}
	
	
	@JsonIgnore
	public void setCategory() {
		this.category = new Category(getCatId(), getCatName());
		//return this;
	}
	
	@JsonIgnore
	public void putCategory(Category category) {
		this.category = category;
		//return this;
	}
	
	
	
/*	@JsonIgnore
	public void setCategory(Category category) {
		this.category = category;
		//return this;
	} */

	
	

	

	@Override
	public String toString() {
		return String.format("Product [pxtId=%s, pxtName=%s, quantity=%s, price=%s, pxtUri=%s]", pxtId, pxtName,
				quantity, price, pxtUri);
	}
	
	
	
}
