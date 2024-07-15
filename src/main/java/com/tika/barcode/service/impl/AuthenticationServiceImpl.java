package com.tika.barcode.service.impl;



import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.dto.request.SigninRequest;
import com.tika.barcode.dto.response.JwtAuthenticationResponse;
import com.tika.barcode.repo.UserRepository;
import com.tika.barcode.service.AuthenticationService;
import com.tika.barcode.service.JwtService;

import lombok.RequiredArgsConstructor;

/**
 * Service Class for managing {@link AuthenticationServiceImpl}.request
 * 
 * @author Raghu
 */

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse adminSignin(SigninRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new IllegalArgumentException(CommonConstants.INVALIDEMAILORPASSWORD));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).role(user.getRole()).userId(user.getUserId()).userName(user.getUsername()).build();
    }

}