package com.flickart.util;

public class HashPassword {
	public static String hashPassword(String password) {
		return String.valueOf(password.hashCode());
	}
	public static boolean validatePassword(String password, String hash) {
		return String.valueOf(password.hashCode()).equals(hash);
	}
}
