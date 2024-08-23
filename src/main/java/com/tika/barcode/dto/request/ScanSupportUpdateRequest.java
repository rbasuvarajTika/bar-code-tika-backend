package com.tika.barcode.dto.request;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanSupportUpdateRequest {
	
	private String user;
	private Integer supportId;
	private String issueStatus;

}
