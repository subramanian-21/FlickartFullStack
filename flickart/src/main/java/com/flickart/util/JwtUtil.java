package com.flickart.util;

import java.util.Date;
import javax.crypto.SecretKey;

import com.flickart.model.AdminUser;
import com.flickart.model.User;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

	private static final SecretKey ENCRYPTION_KEY_USER = Keys.hmacShaKeyFor("hihellomysecretkey3893jmmjhxnjhdj".getBytes());
	private static final SecretKey ENCRYPTION_KEY_ADMIN = Keys.hmacShaKeyFor("hihellomysecretkey3893issecretijk".getBytes());

	private static final long ADMIN_ACCESS_TOKEN_EXPIRATION = 60 * 60 * 1000L; // 1 hour
	private static final long ADMIN_REFRESH_TOKEN_EXPIRATION = 60 * 60 * 24 * 1000L; // 1 day
	private static final long ACCESS_TOKEN_EXPIRATION = 60 * 60 * 24 * 1000L; // 1 day
	private static final long REFRESH_TOKEN_EXPIRATION = 60 * 60 * 24 * 30 * 1000L; // 30 days

	public static String createAccessTokenAdmin(AdminUser adminUser) {
		return createToken(JsonUtil.stringifyDto(adminUser), ADMIN_ACCESS_TOKEN_EXPIRATION, ENCRYPTION_KEY_ADMIN);
	}

	public static String createRefreshTokenAdmin(AdminUser adminUser) {
		return createToken(JsonUtil.stringifyDto(adminUser), ADMIN_REFRESH_TOKEN_EXPIRATION, ENCRYPTION_KEY_ADMIN);
	}

	public static String createAccessToken(String email) {
		return createToken(email, ACCESS_TOKEN_EXPIRATION, ENCRYPTION_KEY_USER);
	}

	public static String createRefreshToken(String email) {
		return createToken(email, REFRESH_TOKEN_EXPIRATION, ENCRYPTION_KEY_USER);
	}

	private static String createToken(String message, long expiringTime, SecretKey encryptionKey) {
		try {
			Date expiryDate = new Date(System.currentTimeMillis() + expiringTime);
			return Jwts.builder()
					.setSubject(message)
					.setExpiration(expiryDate)
					.signWith(encryptionKey) // Ensure to use the correct signing method
					.compact();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JwtException("Error creating JWT: " + e.getMessage());
		}
	}

	public static String validateTokenAdmin(String compact) {
		return validateToken(compact, ENCRYPTION_KEY_ADMIN);
	}

	public static String validateTokenUser(String compact) {
		return validateToken(compact, ENCRYPTION_KEY_USER);
	}

	public static String validateToken(String compact, SecretKey encryptionKey) {
		try {
			Jws<Claims> jws = Jwts.parser()
					.setSigningKey(encryptionKey)
					.build()
					.parseClaimsJws(compact);
			return jws.getBody().getSubject(); // Use getSubject() instead of accessing payload directly
		} catch (JwtException e) {
			e.printStackTrace();
			throw new JwtException("Invalid JWT token: " + e.getMessage());
		}
	}
}
