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
import com.tika.barcode.constants.ItemDetailsConstant;
import com.tika.barcode.constants.TerritoryConstant;
import com.tika.barcode.dto.request.TerrAlignmentDetailsRequest;
import com.tika.barcode.dto.response.ItemListResponse;
import com.tika.barcode.dto.response.NSServiceResponse;
import com.tika.barcode.dto.response.PageResponseDTO;
import com.tika.barcode.dto.response.TerrAlignmentResponse;
import com.tika.barcode.service.TerrAlignmentService;
import com.tika.barcode.utility.ResponseHelper;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping(TerritoryConstant.TERR_ALIGNMENT_COMMONAPI)
public class TerrAlignmentController {

    @Autowired
    private TerrAlignmentService terrAlignmentService;
    
	@SuppressWarnings("unchecked")
	@GetMapping(TerritoryConstant.TERR_ALIGN_ALL)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<TerrAlignmentResponse>> getAllTerrAllignmentDetails() {
		 
	    return ResponseHelper.createResponse(new NSServiceResponse<List<TerrAlignmentResponse>>(),
	    		terrAlignmentService.getAllTerrAllignmentDetails(), CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
//	
//	@SuppressWarnings("unchecked")
//	@PutMapping(TerritoryConstant.TERR_ALIGNMENT_COMMONAPI)
//	@CrossOrigin(origins = "*")
//	public NSServiceResponse<List<TerrAlignmentResponse>> updateItemDetails(@PathVariable Integer itemId, @RequestBody TerrAlignmentDetailsRequest request){
//		return ResponseHelper.createResponse(new NSServiceResponse<List<TerrAlignmentResponse>>(),
//				TerrAlignmentService.UpdateTerrAlignmentDetails(itemId, request),CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
//		
//	}
	@SuppressWarnings("unchecked")
	@CrossOrigin(origins = "*")
	@PutMapping(TerritoryConstant.UPDATE_TERR_ALIGNMENT)
	public ResponseEntity<String> updateTerrAlignmentDetails(@PathVariable Integer territoryId, 
	                                                         @RequestBody TerrAlignmentResponse terrAlignmentResponse) {
	    terrAlignmentService.updateTerrAlignmentDetails(territoryId, terrAlignmentResponse);
	    return ResponseEntity.ok("Terr alignment details updated successfully.");
   
}
}
