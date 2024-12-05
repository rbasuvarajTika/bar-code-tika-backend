package com.tika.barcode.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.constants.ItemDetailsConstant;

import com.tika.barcode.dto.request.UpdateItemDetailsRequest;
import com.tika.barcode.dto.response.ItemCountResponse;
import com.tika.barcode.dto.response.ItemDetailsResponse;
import com.tika.barcode.dto.response.ItemListResponse;
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
	
	@SuppressWarnings("unchecked")
	@GetMapping(ItemDetailsConstant.GET_ALL_ITEM_DETAILS)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<ItemListResponse>> getAllItemDetailsList() {
		 System.out.println("Processing getAllScanSupport endpoint...");
	    return ResponseHelper.createResponse(new NSServiceResponse<List<ItemListResponse>>(),
	    		itemDetailsService.getAllItemDetails(), CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
	
	@SuppressWarnings("unchecked")
	@PutMapping(ItemDetailsConstant.ITEM_DETAILS_UPDATE)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<ItemListResponse>> updateItemDetails(@PathVariable Integer itemId, @RequestBody UpdateItemDetailsRequest request){
		return ResponseHelper.createResponse(new NSServiceResponse<List<ItemListResponse>>(),
				itemDetailsService.updateItemDetails(itemId, request),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
	
	@SuppressWarnings("unchecked")
	@CrossOrigin(origins = "*")
	@GetMapping(ItemDetailsConstant.ITEM_ID_COUNT)
    public ItemCountResponse getUserIdCount() {
        Long count = itemDetailsService.getItemIdCount();  
        return new ItemCountResponse(count);  
    }
}
