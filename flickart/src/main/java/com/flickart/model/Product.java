package com.flickart.model;

import java.util.List;

public class Product {
	private String productId;
	private String productName;
	private String productDescription;
	private double price;
	private String category;
	private String brand;
	private double discount;
	private String warranty;
	private double rating;
	private  int ratingCount;
	private int stockCount;
	private String image;
	private List<String> images;
	private List<Review> reviews;
	private String createdAt;

	public Product(String productId, String productName, String productDescription, double price, String category, String brand, double discount, String warranty, double rating, int ratingCount, int stockCount, String image, List<String> images, List<Review> reviews,String createdAt) {
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.price = price;
		this.category = category;
		this.brand = brand;
		this.discount = discount;
		this.warranty = warranty;
		this.rating = rating;
		this.ratingCount = ratingCount;
		this.stockCount = stockCount;
		this.image = image;
		this.images = images;
		this.reviews = reviews;
		this.createdAt = createdAt;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getWarranty() {
		return warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStockCount() {
		return stockCount;
	}
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	
}
