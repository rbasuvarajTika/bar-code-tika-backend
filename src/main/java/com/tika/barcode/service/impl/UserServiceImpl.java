package com.tika.barcode.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
		query.setParameter("PASSWORD", request.getPassword());
		query.setParameter("CONFIRM_PASSWORD", request.getConfirmPassword());
		query.setParameter("PASSWORD_UPDATED_DATE", request.getPasswordUpdatedDate());
		query.setParameter("USER_TYPE", request.getType());
		query.setParameter("OTHER_PASSWORD", request.getOtherPassword());
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

}
