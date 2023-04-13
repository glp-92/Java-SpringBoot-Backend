package com.authregservice.service;

import java.security.Key;
import java.util.Map;


public interface JwtService {
	void validateToken(final String token);
	String generateToken(String username);
}
