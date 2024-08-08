package com.tika.barcode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.constants.InventoryConstant;
import com.tika.barcode.dto.request.CloseInventoryRequest;
import com.tika.barcode.dto.request.InventoryReconRequest;
import com.tika.barcode.dto.response.InventoryRecCloseDetailResponse;
import com.tika.barcode.dto.response.InventoryRecDetailResponse;
import com.tika.barcode.dto.response.InventoryReconResonse;
import com.tika.barcode.dto.response.NSServiceResponse;
import com.tika.barcode.service.InventoryService;
import com.tika.barcode.utility.ResponseHelper;

@RestController
@RequestMapping(InventoryConstant.INV_COMMONAPI)
@CrossOrigin(origins = "*")
public class InventoryController {
	
	@Autowired
	private InventoryService inventoryService;
	
	@SuppressWarnings("unchecked")
	@PostMapping(InventoryConstant.INV_INSERT)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<String> insertInventory(
			@RequestBody List<InventoryReconRequest> request){
		return ResponseHelper.createResponse(new NSServiceResponse<String>(),
				inventoryService.insertInventoryRecon(request),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(InventoryConstant.INV_REC_BY_USER)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<InventoryReconResonse>> getInvRecByAccIdAndUser(
			@RequestParam String user){
		return ResponseHelper.createResponse(new NSServiceResponse<List<InventoryReconResonse>>(),
				inventoryService.getInvRecByAccIdAndUser(user),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(InventoryConstant.INV_REC_DETAIL)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<InventoryRecDetailResponse>> getInvRecDetail(){
		return ResponseHelper.createResponse(new NSServiceResponse<List<InventoryRecDetailResponse>>(),
				inventoryService.getInvRecDetails(),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(InventoryConstant.INV_REC_CLOSE_DETAIL)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<InventoryRecCloseDetailResponse>> getInvRecDetailClose(){
		return ResponseHelper.createResponse(new NSServiceResponse<List<InventoryRecCloseDetailResponse>>(),
				inventoryService.getInvRecCloseDetails(),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(InventoryConstant.INV_CLOSE)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<String> closeInventory(@RequestBody CloseInventoryRequest closeInventoryRequest){
		return ResponseHelper.createResponse(new NSServiceResponse<String>(),
				inventoryService.closeInventoryRecon(closeInventoryRequest),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
	
}
