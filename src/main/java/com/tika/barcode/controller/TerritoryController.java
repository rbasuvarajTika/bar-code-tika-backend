package com.tika.barcode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.constants.ScanSupportConstant;
import com.tika.barcode.constants.TerritoryConstant;
import com.tika.barcode.dto.request.ScanSupportUpdateRequest;
import com.tika.barcode.dto.request.UpdateItemDetailsRequest;
import com.tika.barcode.dto.request.UpdateTerritoryDetailsRequest;
import com.tika.barcode.dto.request.UpdateTerritoryRequest;
import com.tika.barcode.dto.response.NSServiceResponse;
import com.tika.barcode.dto.response.PageResponseDTO;
import com.tika.barcode.dto.response.ScanSupportResponse;
import com.tika.barcode.dto.response.TerrAlignmentResponse;
import com.tika.barcode.dto.response.TerritoryResponse;
import com.tika.barcode.service.TerritoryService;
import com.tika.barcode.utility.ResponseHelper;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping(TerritoryConstant.TERRITORY_COMMONAPI)
public class TerritoryController {

    @Autowired
    private TerritoryService territoryService;

	@SuppressWarnings("unchecked")
	@GetMapping(TerritoryConstant.TERRITORY_LIST_ALL)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<TerritoryResponse>> getAllTerritoryDetails() {
		 
	    return ResponseHelper.createResponse(new NSServiceResponse<List<TerrAlignmentResponse>>(),
	    		territoryService.getAllTerritoryDetails(), CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
    
//    @SuppressWarnings("unchecked")
//	@PutMapping(TerritoryConstant.TERRITORY_DETAILS_UPDATE)
//	@CrossOrigin(origins = "*")
//	public NSServiceResponse<List<TerritoryResponse>> updateTerritoryDetails(@PathVariable Integer territoryId, @RequestBody TerritoryResponse  request){
//		return ResponseHelper.createResponse(new NSServiceResponse<List<TerritoryResponse>>(),
//				territoryService.updateTerritoryDetails(territoryId, request),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
//		
//	}
	
	@SuppressWarnings("unchecked")
	@CrossOrigin(origins = "*")
	@PutMapping(TerritoryConstant.TERRITORY_DETAILS_UPDATE)
	public ResponseEntity<String> updateTerritoryDetails(@PathVariable Integer territoryId, 
	                                                         @RequestBody UpdateTerritoryDetailsRequest updateTerritory) {
		territoryService.updateTerritoryDetails(territoryId, updateTerritory);
	    return ResponseEntity.ok("Territory details updated successfully.");
   
}
}

