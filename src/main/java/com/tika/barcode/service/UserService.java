package com.tika.barcode.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tika.barcode.dto.request.SignUpRequest;
import com.tika.barcode.dto.response.UserDetailsResponse;


/**
 * Service interface for managing {@link UserService}.request
 * 
 * @author Raghu
 */
public interface UserService {
	
	/** Create a new user in UserInfo */
	String createUser(SignUpRequest request);

	UserDetailsService userDetailsService();
	
	public List<UserDetailsResponse> getUserByUserId(Integer userId);
	
	List<UserDetailsResponse> getAllUserDetails();
	
	String updateUser(Integer userId, UserDetailsResponse userDetails);
	
	public Long getUserIdCount();

}