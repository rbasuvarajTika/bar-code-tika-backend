package com.tika.barcode.service;

import com.tika.barcode.dto.request.SigninRequest;
import com.tika.barcode.dto.response.JwtAuthenticationResponse;

/**
 * Service interface for managing {@link AuthenticationService}.request
 * 
 * @author Raghu
 */

public interface AuthenticationService {

	/** Perform authentication for admin users. */
	JwtAuthenticationResponse adminSignin(SigninRequest request);

}