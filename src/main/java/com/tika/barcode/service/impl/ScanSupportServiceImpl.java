package com.tika.barcode.service.impl;

import java.sql.Timestamp;

import java.util.List;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tika.barcode.constants.ParameterConstant;
import com.tika.barcode.constants.ProcedureConstant;
import com.tika.barcode.constants.QueryConstant;
import com.tika.barcode.dto.request.AddNotificationTranRequest;
import com.tika.barcode.dto.request.ScanSupportRequest;
import com.tika.barcode.dto.request.ScanSupportUpdateRequest;
import com.tika.barcode.dto.response.NotificationConfEmailResponse;
import com.tika.barcode.dto.response.ScanSupportResponse;
import com.tika.barcode.service.NotificationConfigService;
import com.tika.barcode.service.NotificationTranService;
import com.tika.barcode.service.ScanSupportService;
import com.tika.barcode.utility.EmailService;

import jakarta.mail.*;
import jakarta.persistence.*;

@Service
public class ScanSupportServiceImpl implements ScanSupportService{
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private NotificationConfigService notificationConfigService;
	@Autowired
	EmailService emailService;
	@Autowired
	NotificationTranService notificationTranService;
	
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
			query.setParameter(ParameterConstant.ISSUE_STATUS, ParameterConstant.IN_PROGRESS);

			query.execute();
			return "Scan Support successfully";
		}catch(Exception e) {
			throw new RuntimeException("add scan suppport failed",e);
		}
		
	}
	
	@Override
	public String emailNotification(ScanSupportRequest request) {
		String errorMsg = "NA";
		String deliveryStatus="success";
		
		NotificationConfEmailResponse confEmailResponse = notificationConfigService
				.getNotiConfByName(ParameterConstant.SCAN_SUPPORT_NATIFICATION_NAME);
		try {
		if(confEmailResponse!=null) {
			//String subject =  confEmailResponse.getNotificationSubject().replace("{USER_NAME}",request.getUser());
			String subject =  confEmailResponse.getNotificationSubject().replaceAll("\\{.*?\\}",request.getUser());
			emailService.sendEmail(recipientEmail, subject, 
					request.getIssueDetails());
			return "scan support added and e-mail send successfully";
		}else {
			throw new RuntimeException("Notification Name not found");
		}
			
		} catch (MessagingException e) {
			errorMsg = e.getMessage();
			deliveryStatus="failed";
			throw new RuntimeException("send e-mail failed",e);
		}catch(Exception e) {
			errorMsg = e.getMessage();
			deliveryStatus="failed";
			throw new RuntimeException("send e-mail failed",e);
		}
		finally {
			AddNotificationTranRequest addNotificationTranRequest = new AddNotificationTranRequest();
			addNotificationTranRequest.setNotificationId(confEmailResponse.getNotificationId());
			addNotificationTranRequest.setUser(request.getUser());
			addNotificationTranRequest.setUserEmail(recipientEmail);
			addNotificationTranRequest.setErrorMsg(errorMsg);
			addNotificationTranRequest.setDeliveryStatus(deliveryStatus);
			notificationTranService.addNotifficationTran(addNotificationTranRequest);
			
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
	
	@Override
	public List<ScanSupportResponse> getAllScanSupports() {
	    Query nativeQuery = entityManager.createNativeQuery(QueryConstant.GET_ALL_SCANSUPPORT);
	    List<Object[]> queryResult = nativeQuery.getResultList();
	    List<ScanSupportResponse> scanSupportResponse = queryResult.stream()
	            .map(this::mapToObjectArrayScanSupportResponse).collect(Collectors.toList());
	    return scanSupportResponse;
	}

	@Override
	public String updateScanSupport(ScanSupportUpdateRequest updateScanSupport) {
	try {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(ProcedureConstant.USP_SCAN_SUPPORT_EDIT);
		query.registerStoredProcedureParameter(ParameterConstant.USER, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.SUPPORT_ID, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ISSUE_STATUS, String.class, ParameterMode.IN);

		query.setParameter(ParameterConstant.USER, updateScanSupport.getUser());
		query.setParameter(ParameterConstant.SUPPORT_ID, updateScanSupport.getSupportId());
		query.setParameter(ParameterConstant.ISSUE_STATUS, updateScanSupport.getIssueStatus());

		query.execute();
		return "Scan Support updated successfully";
	}catch(Exception e) {
		throw new RuntimeException(" scan suppport update failed",e);
	}
		
	}

	@Override
	public Long getAllSupportIdCount() {
		Query query = entityManager.createNativeQuery(QueryConstant.TOTAL_SUPPORT_ID_COUNT_DIM_SCAN_SUPPORT);
		return ((Number) query.getSingleResult()).longValue() ;
	}

}
