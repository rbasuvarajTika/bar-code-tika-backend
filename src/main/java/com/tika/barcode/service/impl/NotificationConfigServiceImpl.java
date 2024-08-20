package com.tika.barcode.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.tika.barcode.constants.QueryConstant;
import com.tika.barcode.dto.response.NotificationConfEmailResponse;
import com.tika.barcode.service.NotificationConfigService;

@Service
public class NotificationConfigServiceImpl implements NotificationConfigService{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public NotificationConfEmailResponse getNotiConfByName(String notificationName) {
		Query nativeQuery = entityManager.createNativeQuery(QueryConstant.SELECT_NOTICATION_CONF_BY_NAME)
				.setParameter(1, notificationName);
		List<Object[]> queryResult = nativeQuery.getResultList();
		List<NotificationConfEmailResponse> notifConfiResponse = queryResult.stream()
				.map(this::mapToObjectArrayNotifConfigResponse).collect(Collectors.toList());
		return notifConfiResponse.get(0);

	}
	private NotificationConfEmailResponse mapToObjectArrayNotifConfigResponse(Object[] record) {
		NotificationConfEmailResponse response = new NotificationConfEmailResponse();
		response.setNotificationId((Integer) record[0]);
		response.setNotificationName((String) record[1]);
		response.setNotificationSubject((String) record[2]);
		response.setNotificationBody((String) record[3]);

		return response;
	}

}
