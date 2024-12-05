package com.tika.barcode.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.tika.barcode.dto.request.TerrAlignmentDetailsRequest;
import com.tika.barcode.dto.response.ItemListResponse;
import com.tika.barcode.dto.response.PageResponseDTO;
import com.tika.barcode.dto.response.TerrAlignmentResponse;

public interface TerrAlignmentService {
	
	//public PageResponseDTO getAllTerrAlignment(PageRequest pageRequest);
	public List<TerrAlignmentResponse> getAllTerrAllignmentDetails();
	
	void updateTerrAlignmentDetails(Integer territoryId, TerrAlignmentResponse terrAlignmentResponse);
}
