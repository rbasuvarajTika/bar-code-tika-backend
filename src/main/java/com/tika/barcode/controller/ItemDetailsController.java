package com.tika.barcode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.constants.ItemDetailsConstant;
import com.tika.barcode.dto.response.ItemDetailsResponse;
import com.tika.barcode.dto.response.NSServiceResponse;
import com.tika.barcode.dto.response.PageResponseDTO;
import com.tika.barcode.service.ItemDetailsService;
import com.tika.barcode.utility.ResponseHelper;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(ItemDetailsConstant.ITEM_COMMONAPI)
public class ItemDetailsController {


	@Autowired
	private ItemDetailsService itemDetailsService;
	
	@SuppressWarnings("unchecked")
	@GetMapping(ItemDetailsConstant.GET_ITEM_DETAILS)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<ItemDetailsResponse>> getAccountList() {
		return ResponseHelper.createResponse(new NSServiceResponse<PageResponseDTO>(), itemDetailsService.getItemDetails(),
				CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
}
