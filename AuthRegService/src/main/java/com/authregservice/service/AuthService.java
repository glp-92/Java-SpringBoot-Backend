package com.authregservice.service;

import com.authregservice.model.pojo.User;
import com.authregservice.model.request.CreateUserRequest;

public interface AuthService {
	User createUser(CreateUserRequest request);
	String generateToken(String username);
	void validateToken(String token);
}
