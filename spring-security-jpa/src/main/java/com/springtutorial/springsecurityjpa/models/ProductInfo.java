package com.springtutorial.springsecurityjpa.models;

public class ProductInfo {
	
	private ImageModel imageModel;
	
	private Product product;

	public ProductInfo(ImageModel imageModel, Product product) {
		this.imageModel = imageModel;
		this.product = product;
	}

	public ProductInfo() {
	}

	public ImageModel getImageModel() {
		return imageModel;
	}

	public void setImageModel(ImageModel imageModel) {
		this.imageModel = imageModel;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
