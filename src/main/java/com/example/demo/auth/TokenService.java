package com.example.demo.auth;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/*
 * Creating and decoding a token.
 */
public class TokenService {
	
	public static final String  TOKEN_NAME = "X-AUTH-TOKEN";
	public static final String  SECRET_KEY = "secret_key";
	public static final String  ID = "id";
	public static final Integer EXPIRATION_TIME = 10000000;
	
	
	public static String createJWT(Integer userId, long expirationTime) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		JwtBuilder builder = Jwts.builder()
				.claim(ID, userId)
				.signWith(signatureAlgorithm, signingKey);
 
		if (expirationTime >= 0) {
			long expMillis = nowMillis + expirationTime;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
    
     	return builder.compact();
	}

	public static Integer getUserIdFromToken(String token) {
		
		return (Integer) getClaims(token).get(ID);
	}

	private static Claims getClaims(String token) {
		return Jwts.parser()         
				.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
				.parseClaimsJws(token).getBody();
	}

}



