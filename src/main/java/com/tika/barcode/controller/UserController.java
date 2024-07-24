package com.tika.barcode.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.constants.UsersConstant;
import com.tika.barcode.dto.request.SignUpRequest;
import com.tika.barcode.dto.response.NSServiceResponse;
import com.tika.barcode.dto.response.UserDetailsResponse;
import com.tika.barcode.service.UserService;
import com.tika.barcode.utility.ResponseHelper;



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
	@SuppressWarnings("unchecked")
	@PostMapping(UsersConstant.CREATEUSER)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<String> createUser(@RequestBody SignUpRequest request) {
		return ResponseHelper.createResponse(new NSServiceResponse<String>(), userService.createUser(request), CommonConstants.SUCCESSFULLY,
				CommonConstants.ERRROR);
	}
	
	/** Retrieves A list Of ActiveUsers In UserDetails List */
	@SuppressWarnings("unchecked")
	@GetMapping(UsersConstant.USERSDETAILSBYUSERID)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public NSServiceResponse<List<UserDetailsResponse>> getUserById(@PathVariable Integer userId)

	{
		List<UserDetailsResponse> usersList = userService.getUserByUserId(userId);
		return ResponseHelper.createResponse(new NSServiceResponse<UserDetailsResponse>(), usersList, CommonConstants.SUCCESSFULLY,
				CommonConstants.ERRROR);
	}

}