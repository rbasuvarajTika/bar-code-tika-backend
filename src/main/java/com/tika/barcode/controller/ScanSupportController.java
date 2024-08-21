package com.tika.barcode.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.constants.ScanSupportConstant;
import com.tika.barcode.dto.request.ScanSupportRequest;
import com.tika.barcode.dto.request.ScanSupportUpdateRequest;
import com.tika.barcode.dto.response.NSServiceResponse;
import com.tika.barcode.dto.response.ScanSupportResponse;
import com.tika.barcode.service.ScanSupportService;
import com.tika.barcode.utility.ResponseHelper;

@RestController
@RequestMapping(ScanSupportConstant.SCAN_SUPPORT_COMMONAPI)
@CrossOrigin(origins = "*")
public class ScanSupportController {
	
	@Autowired
	private ScanSupportService scanSupportService;
	
	@SuppressWarnings("unchecked")
	@PostMapping(ScanSupportConstant.SCAN_SUPPORT_ADD)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<String> addScanSupport(
			@RequestBody ScanSupportRequest request){
		String response = scanSupportService.addScanSupport(request);
		if(response!=null) {
			response = scanSupportService.emailNotification(request);
		}
		return ResponseHelper.createResponse(new NSServiceResponse<String>(),
				response,CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
	@SuppressWarnings("unchecked")
	@GetMapping(ScanSupportConstant.SCAN_SUPPORT_GET)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<ScanSupportResponse>> getScanSupport(@RequestParam String user){
		return ResponseHelper.createResponse(new NSServiceResponse<List<ScanSupportResponse>>(),
				scanSupportService.getAllScanSupport(user),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
	@SuppressWarnings("unchecked")
	@PutMapping(ScanSupportConstant.SCAN_SUPPORT_UPDATE)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<ScanSupportResponse>> updateScanSupport(@RequestBody ScanSupportUpdateRequest request){
		return ResponseHelper.createResponse(new NSServiceResponse<List<ScanSupportResponse>>(),
				scanSupportService.updateScanSupport(request),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}

}
