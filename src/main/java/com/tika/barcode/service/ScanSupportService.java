package com.tika.barcode.service;

import java.util.List;

import com.tika.barcode.dto.request.ScanSupportRequest;
import com.tika.barcode.dto.response.ScanSupportResponse;

public interface ScanSupportService {

	public String addScanSupport(ScanSupportRequest scanSupportRequest);
	
	public List<ScanSupportResponse> getAllScanSupport(String user);
	
	public String emailNotification(ScanSupportRequest request);
}
