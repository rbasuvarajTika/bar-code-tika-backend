package com.tika.barcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.constants.InventoryConstant;
import com.tika.barcode.dto.request.AddInventoryRequest;
import com.tika.barcode.dto.request.InitiateInventoryRequest;
import com.tika.barcode.dto.request.ModifyInventoryRequest;
import com.tika.barcode.dto.request.SubmitInventoryRequest;
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
	@PostMapping(InventoryConstant.INV_INITIATE)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<String> initiateInventory(
			@RequestBody InitiateInventoryRequest request){
		return ResponseHelper.createResponse(new NSServiceResponse<String>(),
				inventoryService.initiateInventoryProc(request),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(InventoryConstant.INV_ADD)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<String> addInventory(
			@RequestBody AddInventoryRequest request){
		return ResponseHelper.createResponse(new NSServiceResponse<String>(),
				inventoryService.addInventoryProc(request),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(InventoryConstant.INV_MOD)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<String> modifyInventory(
			@RequestBody ModifyInventoryRequest request){
		return ResponseHelper.createResponse(new NSServiceResponse<String>(),
				inventoryService.modifyInventoryProc(request),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}

	@SuppressWarnings("unchecked")
	@PostMapping(InventoryConstant.INV_SUBMIT)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<String> submitInventory(
			@RequestBody SubmitInventoryRequest request){
		return ResponseHelper.createResponse(new NSServiceResponse<String>(),
				inventoryService.submitInventoryProc(request),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
}
