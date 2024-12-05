package com.tika.barcode.service;

import java.util.List;



import com.tika.barcode.dto.request.UpdateTerritoryDetailsRequest;
import com.tika.barcode.dto.request.UpdateTerritoryRequest;

import com.tika.barcode.dto.response.TerritoryResponse;

public interface TerritoryService {
	
	
	public List<TerritoryResponse> getAllTerritoryDetails();
	
	//public String updateTerritoryDetails(Integer territoryId, UpdateTerritoryDetailsRequest updateTerritoryDetails);
	
	public void updateTerritoryDetails(Integer territoryId, UpdateTerritoryDetailsRequest updateTerritory);
	
	//public PageResponseDTO getAllTerritoryList(PageRequest pageRequest);
	
}
