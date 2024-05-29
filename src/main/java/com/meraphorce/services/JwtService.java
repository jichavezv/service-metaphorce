package com.meraphorce.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Class for service for Security JWT
 * @author Juan Chavez
 * @since May/28/2024
 */
@Service
public class JwtService {
	@Value("${security.jwt.secret-key}")
	private String secretKey;

	/**
	 * Generate Token for Authentication
	 * @param userName User
	 * @return String with Authorized Token
	 * @author Juan Chavez
	 * @since May/28/2024
	 */
	public String generateToken(String userName) {
		return buildToken(userName);
	}

	/**
	 * Build Token with expiration and signed 
	 * @param userName User
	 * @return String of the Token for authentication
	 * @author Juan Chavez
	 * @since May/28/2024
	 */
	private String buildToken(String userName) {
		return Jwts.builder()
				.setClaims(new HashMap<>())
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	/**
	 * Gets the signed Key 
	 * @return Decoded Key 
	 * @author Juan Chavez
	 * @since May/28/2024
	 */
	private Key getSignKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

	/**
	 * Get the user name of the Token
	 * @param token Token
	 * @return String with the User name
	 * @author Juan Chavez
	 * @since May/28/2024
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Get the expiration of Token
	 * @param token Token
	 * @return Date when Token expire
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * Get a value for the claims on token
	 * @param <T> Datatype for the claim
	 * @param token Token
	 * @param claimsResolver Function on extract
	 * @return Value for the claim
	 * @author Juan Chavez
	 * @since May/28/2024
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	/**
	 * Get all claims of Token
	 * @param token
	 * @return Set of claims
	 * @author Juan Chavez
	 * @since May/28/2024
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	/**
	 * Check if a token is expired
	 * @param token
	 * @return TRUE / FALSE
	 * @author Juan Chavez
	 * @since May/28/2024
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * Validate if a Token is correct
	 * @param token
	 * @param userDetails
	 * @return TRUE / FALSE
	 * @author Juan Chavez
	 * @since May/28/2024
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
}
