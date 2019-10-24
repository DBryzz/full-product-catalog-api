package com.bryzz.fullcatalog.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
@Entity
@Table(name = "category_tbl")
public class Category {

	@Id
	@GeneratedValue
	private int catId;
	private String catName;
	
	@JsonIgnore
	@OneToMany(mappedBy="category")
	private List<Product> products;
	
	
	
	
	// Constuctors
	public Category() {
		
	}
	
	public Category(int catId) {
		this.catId = catId;
			
	}
	
	
	public Category(int catId, String catName) {
		super();
		this.catId = catId;
		this.catName = catName;
		
	}
	
	
	// Getters and Setters
	
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	
	
	
	
	public List<Product> getProducts() { return products; }
	 

	
	public void setProducts(List<Product> products) {
		this.products = products;
	} 


	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}


	@Override
	public String toString() {
		return String.format("Category [catId=%s, catName=%s]", catId, catName);
	}
	
	
	
}
