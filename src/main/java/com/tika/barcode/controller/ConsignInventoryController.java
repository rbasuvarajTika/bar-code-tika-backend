package com.tika.barcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.constants.ConsignInventoryConstant;
import com.tika.barcode.dto.response.NSServiceResponse;
import com.tika.barcode.dto.response.PageResponseDTO;
import com.tika.barcode.service.ConsignInventoryService;
import com.tika.barcode.utility.ResponseHelper;

@RestController
@RequestMapping(ConsignInventoryConstant.CONSIGNMENT_INV_COMMONAPI)
public class ConsignInventoryController {
	
	@Autowired
	private ConsignInventoryService consignInventoryService;
	
	@SuppressWarnings("unchecked")
	@GetMapping(ConsignInventoryConstant.CONSIGNMENT_INV_BY_ACCID)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<PageResponseDTO> getAccountList(
			@PathVariable Integer accountId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return ResponseHelper.createResponse(new NSServiceResponse<PageResponseDTO>(),
				consignInventoryService.getConsignInventByAccId(accountId,pageRequest),
				CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(ConsignInventoryConstant.CONSIGNMENT_INV_ALL)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<PageResponseDTO> getAccountList(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return ResponseHelper.createResponse(new NSServiceResponse<PageResponseDTO>(),
				consignInventoryService.getAllConsignInvent(pageRequest),
				CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}


}
