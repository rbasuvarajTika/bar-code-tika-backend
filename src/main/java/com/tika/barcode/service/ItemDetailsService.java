package com.tika.barcode.service;

import java.util.List;

import com.tika.barcode.dto.request.ScanSupportUpdateRequest;
import com.tika.barcode.dto.request.UpdateItemDetailsRequest;
import com.tika.barcode.dto.response.ItemDetailsResponse;
import com.tika.barcode.dto.response.ItemListResponse;


public interface ItemDetailsService {
	
	public List<ItemDetailsResponse> getItemDetails();
	
	public List<ItemListResponse> getAllItemDetails();
	
	public String updateItemDetails(Integer itemId, UpdateItemDetailsRequest updateItemDetails);
	
	public Long getItemIdCount();
			

}
