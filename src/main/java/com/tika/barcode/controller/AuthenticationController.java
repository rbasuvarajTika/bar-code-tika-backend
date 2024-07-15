package com.tika.barcode.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.dto.request.SigninRequest;
import com.tika.barcode.dto.response.JwtAuthenticationResponse;
import com.tika.barcode.service.AuthenticationService;

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
    @PostMapping("/signin")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.adminSignin(request));
    }
}
