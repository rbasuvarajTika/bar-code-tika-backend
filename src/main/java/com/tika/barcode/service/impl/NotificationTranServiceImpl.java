package com.tika.barcode.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;

import com.tika.barcode.constants.ParameterConstant;
import com.tika.barcode.constants.ProcedureConstant;
import com.tika.barcode.dto.request.AddNotificationTranRequest;
import com.tika.barcode.service.NotificationTranService;

@Service
public class NotificationTranServiceImpl implements NotificationTranService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String addNotifficationTran(AddNotificationTranRequest notificationTranRequest) {
		try {
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery(ProcedureConstant.USP_NOTIFICATION_TRN_ADD);
			query.registerStoredProcedureParameter(ParameterConstant.USER, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(ParameterConstant.USER_EMAIL, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(ParameterConstant.ERROR_MSG, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(ParameterConstant.NOTIFCATION_ID, Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(ParameterConstant.DELIVERY_STATUS, String.class, ParameterMode.IN);

			query.setParameter(ParameterConstant.USER, notificationTranRequest.getUser());
			query.setParameter(ParameterConstant.USER_EMAIL, notificationTranRequest.getUserEmail());
			query.setParameter(ParameterConstant.ERROR_MSG, notificationTranRequest.getErrorMsg());
			query.setParameter(ParameterConstant.NOTIFCATION_ID, notificationTranRequest.getNotificationId());
			query.setParameter(ParameterConstant.DELIVERY_STATUS, notificationTranRequest.getDeliveryStatus());

			query.execute();
			return "Notification Tran successfully Added";
		}catch(Exception e) {
			throw new RuntimeException("add Notification Tran failed",e);
		}
		
	}

}
