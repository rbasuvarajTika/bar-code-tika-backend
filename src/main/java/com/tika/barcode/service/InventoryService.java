package com.tika.barcode.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.tika.barcode.dto.request.AddInventoryRequest;
import com.tika.barcode.dto.request.CloseInventoryRequest;
import com.tika.barcode.dto.request.InitiateInventoryRequest;
import com.tika.barcode.dto.request.InventoryReconRequest;
import com.tika.barcode.dto.request.ModifyInventoryRequest;
import com.tika.barcode.dto.request.SubmitInventoryRequest;
import com.tika.barcode.dto.response.InventoryRecCloseDetailResponse;
import com.tika.barcode.dto.response.InventoryRecDetailResponse;
import com.tika.barcode.dto.response.InventoryReconResponse;
import com.tika.barcode.dto.response.InventoryReconcileCollectiveResponse;



public interface InventoryService {
	
	public Integer initiateInventoryProc(InitiateInventoryRequest initiateInventoryRequest);
	public void addInventoryProc(AddInventoryRequest addInventoryRequest);
	public String modifyInventoryProc(ModifyInventoryRequest modifyInventoryRequest);
	public String submitInventoryProc(SubmitInventoryRequest submitInventoryRequest);
	
	public String insertInventoryRecon(List<InventoryReconRequest> inventoryReconRequest);
	
	public List<InventoryReconResponse> getInvRecByAccIdAndUser(String user);
	
	public List<InventoryRecDetailResponse> getInvRecDetails(String username);
	
	public List<InventoryRecCloseDetailResponse> getInvRecCloseDetails(String username,Integer trnInvRecId);
	
	public String closeInventoryRecon(CloseInventoryRequest closeInventoryRequest);
	
	public List<InventoryRecCloseDetailResponse> getInvRecCloseDetByTrnInvRecId(Integer trnInvRecId);
	
	//public byte[] generatePdf(Integer trnInvRecId) throws IOException;
	public byte[] createInventoryPdf(Integer trnInvRecId) throws Exception;
	
	public String getEmail(String user);

	public String sendInventoryPdf(Integer trnInvRecId,String username);
	
	//public PageResponseDTO getAllCollectiveInventoryReconcile(PageRequest pageRequest);
	
	public List<InventoryReconcileCollectiveResponse> getAllCollectiveInventoryReconcile();
	
	public String updateInventoryDetails(Integer trnInvRecId, InventoryReconcileCollectiveResponse updateRequest);
}