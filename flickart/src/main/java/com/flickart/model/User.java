package com.flickart.model;

import java.util.List;

import com.google.gson.annotations.Expose;

public class User {
	private String userId;
	private String userName;
	private String email;
	private String password;
	private String profilePhoto;
	private Cart cart;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}


	public User(String userId, String userName, String email, String password, String profilePhoto, Cart cart) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.profilePhoto = profilePhoto;
		this.cart = cart;
	}
	
	
}
