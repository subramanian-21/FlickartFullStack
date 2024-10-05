package com.flickart.util;

import java.util.Date;
import javax.crypto.SecretKey;

import com.flickart.model.AdminUser;
import com.flickart.model.User;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

	private static final SecretKey ENCRYPTION_KEY_USER = Keys.hmacShaKeyFor("hihellomysecretkey3893jmmjhxnjhdj".getBytes());
	private static final SecretKey ENCRYPTION_KEY_ADMIN = Keys.hmacShaKeyFor("hihellomysecretkey3893issecretijk".getBytes());;

	private static final long ADMIN_ACCESS_TOKEN_EXPIRATION = 60*60*1000L;
	private static final long ADMIN_REFRESH_TOKEN_EXPIRATION = 60*60*24*1000L;
	private static final long ACCESS_TOKEN_EXPIRATION = 60*60*24*1000L; // 1 day in ms
	private static final long REFRESH_TOKEN_EXPIRATION = 60*60*24*30*1000L; // 30 days in ms
	
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

	private static String createToken(String message, long expairingTime, SecretKey ENCRYPTION_KEY) {
		try {
			Date expairyDate = new Date(System.currentTimeMillis() + expairingTime);
			AeadAlgorithm enc = Jwts.ENC.A256GCM;
			String compact = Jwts.builder().subject(message).expiration(expairyDate).signWith(ENCRYPTION_KEY).compact();
			return compact;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public static  String validateTokenAdmin(String compact){
		return validateToken(compact, ENCRYPTION_KEY_ADMIN);
	}
	public static  String validateTokenUser(String compact){
		return validateToken(compact, ENCRYPTION_KEY_USER);
	}
	public static String validateToken(String compact, SecretKey ENCRYPTION_KEY) {
		try {
			Jws<Claims> jwe = Jwts.parser().setSigningKey(ENCRYPTION_KEY).build().parseClaimsJws(compact);
			return jwe.getPayload().get("sub").toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
