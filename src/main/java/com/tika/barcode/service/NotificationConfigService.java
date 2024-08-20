package com.tika.barcode.service;

import com.tika.barcode.dto.response.NotificationConfEmailResponse;

public interface NotificationConfigService {

	public NotificationConfEmailResponse getNotiConfByName(String notificationName);
}
