package com.tika.barcode.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	@GetMapping(AccountConstant.ACC_LIST_ALL)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<AccountResponse>> getAccountList(@RequestParam String user) {
		return ResponseHelper.createResponse(new NSServiceResponse<PageResponseDTO>(), accountService.getAccountList(user),
				CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(AccountConstant.ACC_DETAILS_ALL)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<AcoountDetailsResponse>> getAllAccountDetails(@RequestParam String user) {
		return ResponseHelper.createResponse(new NSServiceResponse<PageResponseDTO>(), accountService.getAllAccountDetails(user),
				CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
		

}

