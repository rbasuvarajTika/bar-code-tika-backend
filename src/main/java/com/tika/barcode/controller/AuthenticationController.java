package com.tika.barcode.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.dto.request.SigninRequest;
import com.tika.barcode.dto.response.JwtAuthenticationResponse;
import com.tika.barcode.dto.response.NSServiceResponse;
import com.tika.barcode.service.AuthenticationService;
import com.tika.barcode.utility.ResponseHelper;

import lombok.RequiredArgsConstructor;

/**
 * Processes an {@link AuthenticationController } .controller
 * @author Raghu
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping(CommonConstants.APIV1AUTH)
@RequiredArgsConstructor
public class AuthenticationController {
	
    private final AuthenticationService authenticationService;
    
    /** Login a User.*/
    @SuppressWarnings("unchecked")
	@PostMapping("/signin")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public NSServiceResponse<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
    	return ResponseHelper.createResponse(new NSServiceResponse<JwtAuthenticationResponse>(), 
    			authenticationService.adminSignin(request),
				CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
    }
}
