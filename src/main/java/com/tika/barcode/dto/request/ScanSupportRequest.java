package com.tika.barcode.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanSupportRequest {
	
	private String user;
	private String userEmail;
	private String issueDetails;
	private String issueStatus;

}
