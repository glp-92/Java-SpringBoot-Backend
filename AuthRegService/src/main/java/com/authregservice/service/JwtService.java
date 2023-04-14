package com.authregservice.service;

public interface JwtService {
	boolean validateToken(final String token);
	String generateToken(String username);
}
