package com.tika.barcode.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tika.barcode.constants.ParameterConstant;
import com.tika.barcode.constants.ProcedureConstant;
import com.tika.barcode.constants.QueryConstant;
import com.tika.barcode.dto.request.ScanSupportRequest;
import com.tika.barcode.dto.response.NotificationConfEmailResponse;
import com.tika.barcode.dto.response.ScanSupportResponse;
import com.tika.barcode.service.NotificationConfigService;
import com.tika.barcode.service.ScanSupportService;
import com.tika.barcode.utility.EmailService;

@Service
public class ScanSupportServiceImpl implements ScanSupportService{
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private NotificationConfigService notificationConfigService;
	@Autowired
	EmailService emailService;
	
	 @Value("${support.details.recipient.email}")
	    private String recipientEmail;

	@Override
	public String addScanSupport(ScanSupportRequest scanSupportRequest) {
		try {
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery(ProcedureConstant.USP_SCAN_SUPPORT_ADD);
			query.registerStoredProcedureParameter(ParameterConstant.USER, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(ParameterConstant.USER_EMAIL, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(ParameterConstant.ISSUE_DETAILS, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(ParameterConstant.ISSUE_STATUS, String.class, ParameterMode.IN);

			query.setParameter(ParameterConstant.USER, scanSupportRequest.getUser());
			query.setParameter(ParameterConstant.USER_EMAIL, scanSupportRequest.getUserEmail());
			query.setParameter(ParameterConstant.ISSUE_DETAILS, scanSupportRequest.getIssueDetails());
			query.setParameter(ParameterConstant.ISSUE_STATUS, scanSupportRequest.getIssueStatus());

			query.execute();
			return "Scan Support successfully";
		}catch(Exception e) {
			throw new RuntimeException("add scan suppport failed",e);
		}
		
	}
	
	@Override
	public String emailNotification(ScanSupportRequest request) {
		try {
		NotificationConfEmailResponse confEmailResponse = notificationConfigService
				.getNotiConfByName(ParameterConstant.SCAN_SUPPORT_NATIFICATION_NAME);
		if(confEmailResponse!=null) {
			//String subject =  confEmailResponse.getNotificationSubject().replace("{USER_NAME}",request.getUser());
			String subject =  confEmailResponse.getNotificationSubject().replaceAll("\\{.*?\\}",request.getUser());
			emailService.sendEmail("rbasuvaraj@tikamobile.com", subject, 
					request.getIssueDetails());
			return "scan support added and e-mail send successfully";
		}else {
			throw new RuntimeException("Notification Name not found");
		}
			
		} catch (MessagingException e) {
			throw new RuntimeException("send e-mail failed",e);
		}
	}

	private Integer retrievePrimaryKey(ScanSupportRequest scanSupportRequest) {

		Query nativeQuery = entityManager.createNativeQuery(QueryConstant.RETRIVE_SCAN_SUPPORT_PK);
		nativeQuery.setParameter(1, scanSupportRequest.getUser());
		nativeQuery.setParameter(2, scanSupportRequest.getUserEmail());

		// Execute the query and retrieve the primary key
		Integer primaryKey = (Integer) nativeQuery.getSingleResult();

		return primaryKey;
	}
	
	@Override
	public List<ScanSupportResponse> getAllScanSupport(String user) {
		Query nativeQuery = entityManager.createNativeQuery(QueryConstant.GET_ALL_SCAN_SUPPORT)
				.setParameter(1, user);
		List<Object[]> queryResult = nativeQuery.getResultList();
		List<ScanSupportResponse> scanSupportResponse = queryResult.stream()
				.map(this::mapToObjectArrayScanSupportResponse).collect(Collectors.toList());
		return scanSupportResponse;

	}
	private ScanSupportResponse mapToObjectArrayScanSupportResponse(Object[] record) {
		ScanSupportResponse response = new ScanSupportResponse();
		response.setSupportId((Integer) record[0]);
		response.setUserEmail((String) record[1]);
		response.setIssueDetails((String) record[2]);
		response.setIssueStatus((String) record[3]);
		response.setCreateUser((String) record[4]);
		Timestamp createDate = (Timestamp) record[5];
		if (createDate != null)
			response.setCreateDate(createDate.toLocalDateTime());
		response.setUpdateUser((String) record[6]);
		Timestamp updateDate = (Timestamp) record[7];
		if (updateDate != null)
			response.setUpdateDate(updateDate.toLocalDateTime());

		return response;
	}

}