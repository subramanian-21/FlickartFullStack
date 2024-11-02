package com.flickart.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {
	public static String hashPassword(String password) throws NoSuchAlgorithmException{
		StringBuilder hashedPassword = new StringBuilder();
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] bytes = md.digest(password.getBytes());
		for (byte b : bytes) {
			hashedPassword.append(String.format("%02x", b));
		}
		return hashedPassword.toString();
	}
	public static boolean validatePassword(String password, String hash) throws NoSuchAlgorithmException {
		return hashPassword(password).equals(hash);
	}
}
