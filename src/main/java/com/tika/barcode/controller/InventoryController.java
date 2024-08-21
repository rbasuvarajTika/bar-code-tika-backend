package com.tika.barcode.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.tika.barcode.utility.EmailService;
import com.tika.barcode.utility.ResponseHelper;

@RestController
@RequestMapping(InventoryConstant.INV_COMMONAPI)
@CrossOrigin(origins = "*")
public class InventoryController {
	
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private EmailService emailService;
	
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
	public NSServiceResponse<List<InventoryRecDetailResponse>> getInvRecDetail(@RequestParam String username){
		return ResponseHelper.createResponse(new NSServiceResponse<List<InventoryRecDetailResponse>>(),
				inventoryService.getInvRecDetails(username),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(InventoryConstant.INV_REC_CLOSE_DETAIL)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<InventoryRecCloseDetailResponse>> getInvRecDetailClose(@RequestParam String username,@RequestParam Integer trnInvRecId){
		return ResponseHelper.createResponse(new NSServiceResponse<List<InventoryRecCloseDetailResponse>>(),
				inventoryService.getInvRecCloseDetails(username,trnInvRecId),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(InventoryConstant.INV_CLOSE)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<String> closeInventory(@RequestBody CloseInventoryRequest closeInventoryRequest){
		return ResponseHelper.createResponse(new NSServiceResponse<String>(),
				inventoryService.closeInventoryRecon(closeInventoryRequest),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
		
	}
	
	 @GetMapping("/download")
	    public ResponseEntity<byte[]> downloadPdf(@RequestParam Integer trnInvRecId) {
	        try {
	            // Example list; replace with actual data fetching logic
	           // List<InventoryRecCloseDetailResponse> details = fetchInventoryData();

	            // Generate PDF as a byte array
	            byte[] pdfContent = inventoryService.createInventoryPdf(trnInvRecId);

	            // Generate file name
	            String fileName = "InventoryReport_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";

	            // Set headers for download
	            HttpHeaders headers = new HttpHeaders();
	            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
	            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

	            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	    
	    @SuppressWarnings("unchecked")
		@GetMapping("/send-pdf-email")
	    public NSServiceResponse<String> sendEmailWithPdf(@RequestParam Integer trnInvRecId,@RequestParam(required = false) String username) {
	        try {
//	        	String recipientEmail = "rbasuvaraj@tikamobile.com";
//	        	if(username==null) {
//	        		recipientEmail = "rbasuvaraj@tikamobile.com";
//	        	}else {
//	        		recipientEmail=inventoryService.getEmail(username);
//	        	}
	        	
	           // byte[] pdfContent = inventoryService.createInventoryPdf(trnInvRecId);
	           // String fileName = "InventoryReport_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
	           // emailService.sendEmailWithAttachment(recipientEmail, "Inventory Report", "Please find the attached inventory report.", fileName, pdfContent);
	          
	        	inventoryService.sendInventoryPdf(trnInvRecId, username);
	            return ResponseHelper.createResponse(new NSServiceResponse<String>(),
	            		"Email sent successfully.",CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	        } catch (Exception e) {
	           throw new RuntimeException("Email sent failed",e);	        }
	    }


}
