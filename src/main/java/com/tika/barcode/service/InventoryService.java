package com.tika.barcode.service;

import com.tika.barcode.dto.request.AddInventoryRequest;
import com.tika.barcode.dto.request.InitiateInventoryRequest;
import com.tika.barcode.dto.request.ModifyInventoryRequest;
import com.tika.barcode.dto.request.SubmitInventoryRequest;

public interface InventoryService {
	
	public String initiateInventoryProc(InitiateInventoryRequest initiateInventoryRequest);
	public String addInventoryProc(AddInventoryRequest addInventoryRequest);
	public String modifyInventoryProc(ModifyInventoryRequest modifyInventoryRequest);
	public String submitInventoryProc(SubmitInventoryRequest submitInventoryRequest);

}
