package com.tika.barcode.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Service interface for managing {@link JwtService}.request
 * 
 * @author Raghu
 */

public interface JwtService {

	/** Extacts UserName from Token */
	String extractUserName(String token);

	/** GenerateToken from UserDetails */
	String generateToken(UserDetails userDetails);

	/** Check Whether Token is valid or not */
	boolean isTokenValid(String token, UserDetails userDetails);
}