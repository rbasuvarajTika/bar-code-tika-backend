package com.tika.barcode.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNotificationTranRequest {
	private Integer notificationId;
	private String user;
	private String userEmail;
	private String errorMsg;
	private String deliveryStatus;

}
