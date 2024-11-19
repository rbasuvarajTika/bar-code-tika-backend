package com.tika.barcode.service.impl;

import java.util.Date;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tika.barcode.config.CustomPasswordEncoder;
import com.tika.barcode.dto.request.SignUpRequest;
import com.tika.barcode.dto.response.UserDetailsResponse;
import com.tika.barcode.entity.User;
import com.tika.barcode.enums.ErrorCodes;
import com.tika.barcode.exceptions.NSException;
import com.tika.barcode.repo.UserRepository;
import com.tika.barcode.service.UserService;

import jakarta.persistence.*;

import com.tika.barcode.constants.ParameterConstant;
import com.tika.barcode.constants.ProcedureConstant;




/**
 * Service Class for managing {@link UserServiceImpl}.request
 * 
 * @author Raghu
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private CustomPasswordEncoder passwordEncoder;
	
	@Override
	public String createUser(SignUpRequest request) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(ProcedureConstant.USP_APP_USER_ADD);
		query.registerStoredProcedureParameter(ParameterConstant.USER, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.FIRST_NAME, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.MIDDLE_NAME, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.LAST_NAME, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ADDRESS1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ADDRESS2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.CITY, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.STATE, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ZIP, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ROLE, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.USER_NAME, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.USER_EMAIL, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.USER_PHONE, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.USER_MOBILE, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.PREFERRED_NAME, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.SALES_FORCE, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.PASSWORD, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.CONFIRM_PASSWORD, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.PASSWORD_UPDATED_DATE, Date.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.USER_TYPE, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.OTHER_PASSWORD, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.USER_IMAGE_URL, String.class, ParameterMode.IN);
		query.setParameter(ParameterConstant.USER, request.getCreatedUser());
		query.setParameter(ParameterConstant.FIRST_NAME, request.getFirstName());
		query.setParameter(ParameterConstant.MIDDLE_NAME, request.getMiddleName());
		query.setParameter(ParameterConstant.LAST_NAME, request.getLastName());
		query.setParameter(ParameterConstant.ADDRESS1, request.getAddress());
		query.setParameter(ParameterConstant.ADDRESS2, request.getAddress());
		query.setParameter(ParameterConstant.CITY, request.getCity());
		query.setParameter(ParameterConstant.STATE, request.getState());
		query.setParameter(ParameterConstant.ZIP, request.getZip());
		query.setParameter(ParameterConstant.ROLE, request.getRole());
		query.setParameter(ParameterConstant.USER_NAME, request.getUserName());
		query.setParameter(ParameterConstant.USER_EMAIL, request.getEmail());
		query.setParameter(ParameterConstant.USER_PHONE, request.getPhone());
		query.setParameter(ParameterConstant.USER_MOBILE, request.getPhone());
		query.setParameter(ParameterConstant.PREFERRED_NAME, request.getUserName());
		query.setParameter(ParameterConstant.SALES_FORCE, request.getSalesForce());
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		query.setParameter(ParameterConstant.PASSWORD, encodedPassword);
		query.setParameter(ParameterConstant.CONFIRM_PASSWORD, encodedPassword);
		query.setParameter(ParameterConstant.PASSWORD_UPDATED_DATE, request.getPasswordUpdatedDate());
		query.setParameter(ParameterConstant.USER_TYPE, request.getType());
		query.setParameter(ParameterConstant.OTHER_PASSWORD, encodedPassword);
		query.setParameter(ParameterConstant.USER_IMAGE_URL, request.getImage());
		query.execute();
		return "User created successfully";
	}

	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) {
				return userRepository.findByUserName(username)
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			}
		};
	}
	

	public List<UserDetailsResponse> getUserByUserId(Integer userId) {
		List<User> userList = userRepository.findAllCustomByUserId(userId);
		List<UserDetailsResponse> userDetailsList = userList.stream().map(user -> {
			UserDetailsResponse userResponse = new UserDetailsResponse();
			userResponse.setUserId(user.getUserId());
			userResponse.setUserName(user.getUsername());
			userResponse.setFirstName(user.getFirstName());
			userResponse.setMiddleName(user.getMiddleName());
			userResponse.setLastName(user.getLastName());
	//		userResponse.setFullName(user.getFullName());
			userResponse.setUserStatusFlag(user.getUserStatusFlag());
			userResponse.setTitle(user.getTitle());
			userResponse.setRole(user.getRole());
			userResponse.setUserMail(user.getUserMail());
			userResponse.setPhone(user.getPhone());
			userResponse.setUserMobile(user.getUserMobile());
	//		userResponse.setUserEmpID(user.getUserEmpID());
			userResponse.setAddress1(user.getAddress1());
			userResponse.setAddress2(user.getAddress2());
			userResponse.setCity(user.getCity());
			userResponse.setState(user.getState());
			userResponse.setZip(user.getZip());
			userResponse.setPreferredName(user.getPreferredName());
			userResponse.setActiveInd(user.getActiveInd());
			userResponse.setUserTerr(user.getUserTerr());
		   //userResponse.setEmpId(user.getUserEmpID());
			userResponse.setHireDate(user.getHireDate());
			userResponse.setEndDate(user.getEndDate());
			userResponse.setStartDate(user.getStartDate());
			userResponse.setAdmToolsFlag(user.getAdmToolsFlag());
			userResponse.setAttendeeFlag(user.getAttendeeFlag());
			userResponse.setBookingUrl(user.getBookingUrl());
			userResponse.setManagerEmail(user.getManagerEmail());
			userResponse.setUserTimeZone(user.getUserTimeZone());
	//		userResponse.setUserNtId(user.getEmpId());
			userResponse.setOutlookClientId(user.getOutlookClientId());
			userResponse.setOutlookSecretCode(user.getOutlookSecretCode());
			userResponse.setOutlookEmailId(user.getOutlookEmailId());
			userResponse.setSalesForce(user.getSalesForce());

			userResponse.setUpdatedDate(user.getPasswordUpdatedDate());
			userResponse.setUserStatusFlag(user.getUserStatusFlag());
			userResponse.setUserType(user.getUserType());

			userResponse.setUserImageUrl(user.getUserImageUrl());
			userResponse.setCreatedUser(user.getCreatedUser());
			userResponse.setCreatedDate(user.getCreatedDate());
			userResponse.setUpdatedUser(user.getUpdatedUser());

			userResponse.setUpdatedDate(user.getUpdatedDate());
			userResponse.setPassword(user.getPassword());
			userResponse.setConfirmPassword(user.getConfirmPassword());
			return userResponse;
		}).collect(Collectors.toList());

		if (userDetailsList == null) {
			throw new NSException(ErrorCodes.OK, "User Id Not Found");
		}
		return userDetailsList;
	}
		
		@Override
		public List<UserDetailsResponse> getAllUserDetails() {
			List<User> allUsers = userRepository.findAll();  // Fetch all users from the repository
		    List<UserDetailsResponse> userDetailsList = allUsers.stream().map(user -> {
		        UserDetailsResponse userResponse = new UserDetailsResponse();
		        userResponse.setUserId(user.getUserId());
		        userResponse.setUserName(user.getUsername());
		        userResponse.setFirstName(user.getFirstName());
		        userResponse.setMiddleName(user.getMiddleName());
		        userResponse.setLastName(user.getLastName());
		        userResponse.setUserStatusFlag(user.getUserStatusFlag());
		        userResponse.setTitle(user.getTitle());
		        userResponse.setRole(user.getRole());
		        userResponse.setUserMail(user.getUserMail());
		        userResponse.setPhone(user.getPhone());
		        userResponse.setUserMobile(user.getUserMobile());
		        userResponse.setAddress1(user.getAddress1());
		        userResponse.setAddress2(user.getAddress2());
		        userResponse.setCity(user.getCity());
		        userResponse.setState(user.getState());
		        userResponse.setZip(user.getZip());
		        userResponse.setPreferredName(user.getPreferredName());
		        userResponse.setActiveInd(user.getActiveInd());
		        userResponse.setUserTerr(user.getUserTerr());
		        userResponse.setHireDate(user.getHireDate());
		        userResponse.setEndDate(user.getEndDate());
		        userResponse.setStartDate(user.getStartDate());
		        userResponse.setAdmToolsFlag(user.getAdmToolsFlag());
		        userResponse.setAttendeeFlag(user.getAttendeeFlag());
		        userResponse.setBookingUrl(user.getBookingUrl());
		        userResponse.setManagerEmail(user.getManagerEmail());
		        userResponse.setUserTimeZone(user.getUserTimeZone());
		        userResponse.setOutlookClientId(user.getOutlookClientId());
		        userResponse.setOutlookSecretCode(user.getOutlookSecretCode());
		        userResponse.setOutlookEmailId(user.getOutlookEmailId());
		        userResponse.setSalesForce(user.getSalesForce());
		        userResponse.setUpdatedDate(user.getPasswordUpdatedDate());
		        userResponse.setUserType(user.getUserType());
		        userResponse.setUserImageUrl(user.getUserImageUrl());
		        userResponse.setCreatedUser(user.getCreatedUser());
		        userResponse.setCreatedDate(user.getCreatedDate());
		        userResponse.setUpdatedUser(user.getUpdatedUser());
		        userResponse.setUpdatedDate(user.getUpdatedDate());
		        return userResponse;
		    }).collect(Collectors.toList());

		    return userDetailsList;
		}

		@Override
		public String updateUser(Integer userId, UserDetailsResponse userDetails) {
		    Optional<User> existingUser = userRepository.findById(userId);
		    if (existingUser.isPresent()) {
		        User user = existingUser.get();
		        
		        // Map fields from `userDetails` to `user` for update
		        user.setUserName(userDetails.getUserName());
		        user.setUserMail(userDetails.getUserMail());
		        user.setUserMobile(userDetails.getUserMobile());
		        // Continue with other fields...
		        
		        userRepository.save(user); // Save updated user to the database
		        return "User updated successfully";
		    } else {
		        throw new NSException(ErrorCodes.NOT_FOUND, "User with ID " + userId + " not found");
		    }		
	}



}
