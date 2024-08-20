package com.tika.barcode.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationConfEmailResponse {
	
	private Integer notificationId;
	private String notificationName;
	private String notificationSubject;
	private String notificationBody;
	private String notificationStatus;
	

}
