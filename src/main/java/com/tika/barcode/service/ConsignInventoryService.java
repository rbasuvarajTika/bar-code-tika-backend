package com.tika.barcode.service;




import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.tika.barcode.dto.response.ConsignInventoryResponse;

import com.tika.barcode.dto.response.PageResponseDTO;

public interface ConsignInventoryService {
	
	public PageResponseDTO getConsignInventByAccId(Integer accountId,PageRequest pageRequest);
	public PageResponseDTO getAllConsignInvent(PageRequest pageRequest);
	
	public List<ConsignInventoryResponse> getAllConsignmentList();
	
	public Long getTotalConsignInventoryCount();
	
		
}
