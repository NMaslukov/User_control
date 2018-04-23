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
 * Class for creating and decoding a token.
 */
public class TokenService {
	public static final String TOKEN_NAME = "X-AUTH-TOKEN";
	public static final String SECRET_KEY = "secret_key";
	public static final String ID = "id";
	public static final Integer EXPIRATION_TIME = 1000000;
	
public static String createJWT(Integer integer, long ttlMillis) {
		
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
 
    long nowMillis = System.currentTimeMillis();
 
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


    JwtBuilder builder = Jwts.builder()
                                .claim(ID, integer)
                                .signWith(signatureAlgorithm, signingKey);
 
    if (ttlMillis >= 0) {
    long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
    }
    
     	return builder.compact();
}

public static Integer parseJWT(String jwt) {

    Claims claims = Jwts.parser()         
       .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
       .parseClaimsJws(jwt).getBody();
  
     return (Integer) claims.get(ID);
}
}



