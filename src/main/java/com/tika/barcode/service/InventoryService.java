package com.tika.barcode.service;

import java.io.IOException;
import java.util.List;

import com.tika.barcode.dto.request.AddInventoryRequest;
import com.tika.barcode.dto.request.CloseInventoryRequest;
import com.tika.barcode.dto.request.InitiateInventoryRequest;
import com.tika.barcode.dto.request.InventoryReconRequest;
import com.tika.barcode.dto.request.ModifyInventoryRequest;
import com.tika.barcode.dto.request.SubmitInventoryRequest;
import com.tika.barcode.dto.response.InventoryRecCloseDetailResponse;
import com.tika.barcode.dto.response.InventoryRecDetailResponse;
import com.tika.barcode.dto.response.InventoryReconResonse;

public interface InventoryService {
	
	public Integer initiateInventoryProc(InitiateInventoryRequest initiateInventoryRequest);
	public Integer addInventoryProc(AddInventoryRequest addInventoryRequest);
	public String modifyInventoryProc(ModifyInventoryRequest modifyInventoryRequest);
	public String submitInventoryProc(SubmitInventoryRequest submitInventoryRequest);
	
	public String insertInventoryRecon(List<InventoryReconRequest> inventoryReconRequest);
	
	public List<InventoryReconResonse> getInvRecByAccIdAndUser(String user);
	
	public List<InventoryRecDetailResponse> getInvRecDetails();
	
	public List<InventoryRecCloseDetailResponse> getInvRecCloseDetails();
	
	public String closeInventoryRecon(CloseInventoryRequest closeInventoryRequest);
	
	public List<InventoryRecCloseDetailResponse> getInvRecCloseDetByTrnInvRecId(Integer trnInvRecId);
	
	//public byte[] generatePdf(Integer trnInvRecId) throws IOException;
	public byte[] createInventoryPdf(Integer trnInvRecId) throws Exception;

}