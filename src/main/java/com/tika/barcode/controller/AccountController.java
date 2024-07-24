package com.tika.barcode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.AccountConstant;
import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.dto.response.AccountResponse;
import com.tika.barcode.dto.response.AcoountDetailsResponse;
import com.tika.barcode.dto.response.NSServiceResponse;
import com.tika.barcode.dto.response.PageResponseDTO;
import com.tika.barcode.service.AccountService;
import com.tika.barcode.utility.ResponseHelper;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(AccountConstant.ACC_COMMONAPI)
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@SuppressWarnings("unchecked")
	@GetMapping(AccountConstant.ACC_LIST)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<AccountResponse>> getAccountList() {
		return ResponseHelper.createResponse(new NSServiceResponse<PageResponseDTO>(), accountService.getAccountList(),
				CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(AccountConstant.ACC_DETAILS_BY_ACCID)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<AcoountDetailsResponse>> getAccountDetailsByAccId(@PathVariable Integer accountId) {
		return ResponseHelper.createResponse(new NSServiceResponse<PageResponseDTO>(), accountService.getAccountDetailsByAccId(accountId),
				CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(AccountConstant.ACC_LIST_PAGE)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<PageResponseDTO> getAccountList(
			@RequestParam(required = false) String accountName,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return ResponseHelper.createResponse(new NSServiceResponse<PageResponseDTO>(), accountService.getAccountListPagination(accountName,pageRequest),
				CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(AccountConstant.ACC_DETAILS_BY_ACCID_PAGE)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<PageResponseDTO> getAccountDetailsByAccId(
			@PathVariable Integer accountId,
			@RequestParam(defaultValue = "0",required = false) int page,
			@RequestParam(defaultValue = "10",required = false) int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return ResponseHelper.createResponse(new NSServiceResponse<PageResponseDTO>(), 
				accountService.getAccountDetailsByAccIdPagination(accountId,pageRequest),
				CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
	
	

}

