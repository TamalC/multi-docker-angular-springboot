package com.springtutorial.springsecurityjpa.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Product {
	
	@Id
	@Column(name = "id")
	private int id;
	private String name;
	private String description;
	private double price;
//	@Column(name = "pic_id",insertable = false, updatable = false)
	private int picId;
	
	
//	@OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "pic_id", referencedColumnName = "id")
//    private ImageModel imageModel; 
//	
	
	public Product() {
	}
	
	public Product(int id, String name, String description, double price, int picId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.picId = picId;
	}
	
	

	public int getPicId() {
		return picId;
	}

	public void setPicId(int picId) {
		this.picId = picId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", picId =" + picId + "]";
	}

//	public ImageModel getImageModel() {
//		return imageModel;
//	}
//
//	public void setImageModel(ImageModel imageModel) {
//		this.imageModel = imageModel;
//	}
	
	
}
