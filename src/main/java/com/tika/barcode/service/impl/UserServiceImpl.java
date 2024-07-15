package com.tika.barcode.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tika.barcode.dto.response.UserDetailsResponse;
import com.tika.barcode.entity.User;
import com.tika.barcode.enums.ErrorCodes;
import com.tika.barcode.exceptions.NSException;
import com.tika.barcode.config.CustomPasswordEncoder;
import com.tika.barcode.dto.request.SignUpRequest;
import com.tika.barcode.repo.UserRepository;
import com.tika.barcode.service.UserService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

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
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("usp_App_USER_Add");
		query.registerStoredProcedureParameter("USER", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("FIRST_NAME", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("MIDDLE_NAME", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("LAST_NAME", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("ADDRESS1", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("ADDRESS2", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("CITY", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("STATE", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("ZIP", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("ROLE", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("USER_NAME", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("USER_EMAIL", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("USER_PHONE", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("USER_MOBILE", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("PREFERRED_NAME", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("SALES_FORCE", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("PASSWORD", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("CONFIRM_PASSWORD", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("PASSWORD_UPDATED_DATE", Date.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("USER_TYPE", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("OTHER_PASSWORD", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("USER_IMAGE_URL", String.class, ParameterMode.IN);
		query.setParameter("USER", request.getCreatedUser());
		query.setParameter("FIRST_NAME", request.getFirstName());
		query.setParameter("MIDDLE_NAME", request.getMiddleName());
		query.setParameter("LAST_NAME", request.getLastName());
		query.setParameter("ADDRESS1", request.getAddress());
		query.setParameter("ADDRESS2", request.getAddress());
		query.setParameter("CITY", request.getCity());
		query.setParameter("STATE", request.getState());
		query.setParameter("ZIP", request.getZip());
		query.setParameter("ROLE", request.getRole());
		query.setParameter("USER_NAME", request.getUserName());
		query.setParameter("USER_EMAIL", request.getEmail());
		query.setParameter("USER_PHONE", request.getPhone());
		query.setParameter("USER_MOBILE", request.getPhone());
		query.setParameter("PREFERRED_NAME", request.getUserName());
		query.setParameter("SALES_FORCE", request.getSalesForce());
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		query.setParameter("PASSWORD", encodedPassword);
		query.setParameter("CONFIRM_PASSWORD", encodedPassword);
		query.setParameter("PASSWORD_UPDATED_DATE", request.getPasswordUpdatedDate());
		query.setParameter("USER_TYPE", request.getType());
		query.setParameter("OTHER_PASSWORD", encodedPassword);
		query.setParameter("USER_IMAGE_URL", request.getImage());
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



}
