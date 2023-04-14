package com.authregservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authregservice.model.pojo.User;
import com.authregservice.model.request.AuthRequest;
import com.authregservice.model.request.CreateUserRequest;
import com.authregservice.service.AuthService;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public ResponseEntity<User> createUserEntity (
			@RequestBody CreateUserRequest request) {
		try {
			User user = authService.createUser(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/login")
	public String getToken(@RequestBody AuthRequest request) {
		Authentication authenticated = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		if (authenticated.isAuthenticated()) {
            return authService.generateToken(request.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
	}
	
	@GetMapping("/validate")
	public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
		boolean validToken = authService.validateToken(token);
		if (validToken) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
}
