package com.aapeliltd.demo.jwt;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.security.Keys;

@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
	
	private String secretKey;
	private String prefixToken;
	private String expirationTokenAfterDays;
	
	public JwtConfig() {
		// TODO Auto-generated constructor stub
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getPrefixToken() {
		return prefixToken;
	}

	public void setPrefixToken(String prefixToken) {
		this.prefixToken = prefixToken;
	}

	public String getExpirationTokenAfterDays() {
		return expirationTokenAfterDays;
	}

	public void setExpirationTokenAfterDays(String expirationTokenAfterDays) {
		this.expirationTokenAfterDays = expirationTokenAfterDays;
	}
	
	@Bean
	public SecretKey getSecretKeyForSigning() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	public String getAuthorizationHeader() {
		return HttpHeaders.AUTHORIZATION;
	}
	
	
	

}
