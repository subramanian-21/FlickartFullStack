package com.flickart.util;

import java.util.Date;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	private static final SecretKey ENCRYPTION_KEY = Keys.hmacShaKeyFor("hihellomysecretkey3893jmmjhxnjhdj".getBytes());;
	private static final long ACCESS_TOKEN_EXPIRATION = 60*60*24*1000l; // 1 day in ms
	private static final long REFRESH_TOKEN_EXPIRATION = 60*60*24*30*1000l; // 30 days in ms
	
	public static String createAccessToken(String message) {
		return createToken(message, ACCESS_TOKEN_EXPIRATION);
	}
	public static String createRefreshToken(String message) {
		return createToken(message, REFRESH_TOKEN_EXPIRATION);
	}
	private static String createToken(String message, long expairingTime) {
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
	public static boolean validateToken(String compact) {
		try {
			Jws<Claims> jwe = Jwts.parser().setSigningKey(ENCRYPTION_KEY).build().parseClaimsJws(compact);
			
			Long expirationMillis = jwe.getBody().get("exp", Long.class);
			Date now = new Date();
			    
			boolean isValid = expirationMillis != null && new Date(expirationMillis*1000).after(now); 
			return isValid;
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return false;
	}
}
