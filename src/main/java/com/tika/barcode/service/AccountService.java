package com.tika.barcode.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.tika.barcode.dto.response.AccountResponse;
import com.tika.barcode.dto.response.AcoountDetailsResponse;
import com.tika.barcode.dto.response.PageResponseDTO;

public interface AccountService {
	
	public List<AccountResponse> getAccountList();
	public List<AcoountDetailsResponse> getAccountDetailsByAccId(Integer accountId);
	
	public PageResponseDTO getAccountListPagination(PageRequest pageRequest);
	public PageResponseDTO getAccountDetailsByAccIdPagination(Integer accountId,PageRequest pageRequest);
	

}
