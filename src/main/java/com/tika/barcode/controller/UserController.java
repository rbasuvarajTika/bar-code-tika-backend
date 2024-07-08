package com.tika.barcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.constants.UsersConstant;
import com.tika.barcode.dto.request.SignUpRequest;
import com.tika.barcode.service.UserService;

import jakarta.validation.Valid;

/**
 * Processes an {@link UserController } controller.
 * 
 * @author Raghu
 *
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(CommonConstants.APIV1USERS)
public class UserController {

	@Autowired
	private UserService userService;

	/** Create a User in userList */
	@PostMapping(UsersConstant.CREATEUSER)
	@CrossOrigin(origins = "*")
	public ResponseEntity<String> createUser(@Valid @RequestBody SignUpRequest request) {
		return ResponseEntity.ok(userService.createUser(request));
	}

}