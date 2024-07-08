package com.tika.barcode.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tika.barcode.dto.request.SignUpRequest;

/**
 * Service interface for managing {@link UserService}.request
 * 
 * @author Raghu
 */
public interface UserService {
	
	/** Create a new user in UserInfo */
	String createUser(SignUpRequest request);

	UserDetailsService userDetailsService();

}