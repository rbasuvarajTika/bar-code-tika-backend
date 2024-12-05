package com.tika.barcode.service.impl;

import java.math.BigDecimal;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tika.barcode.constants.QueryConstant;
import com.tika.barcode.dto.response.ConsignInventoryResponse;

import com.tika.barcode.dto.response.PageResponseDTO;
import com.tika.barcode.service.ConsignInventoryService;

import jakarta.persistence.*;

@Service
public class ConsignInventoryServiceImpl implements ConsignInventoryService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PageResponseDTO getConsignInventByAccId(Integer accountId, PageRequest pageRequest) {
		//PAG_SELECT_CONS_INV_BY_ACC_ID
		
//		String selectQuery = new StringBuilder().append("SELECT fci.ACCOUNT_ID,\r\n"
//				+ "      fci.TERRITORY_ID\r\n"
//				+ "      ,fci.ITEM_ID\r\n"
//				+ "      ,fci.Batch\r\n"
//				+ "      ,fci.Expiry_Date\r\n"
//				+ "      ,fci.Total_Stock\r\n"
//				+ "      ,fci.MATERIAL_KEY\r\n"
//				+ "      ,fci.CUSTOMER\r\n"
//				+ "      ,fci.CUSTOMER_NAME\r\n"
//				+ "      ,fci.RFRSH_DATE\r\n"
//				+ "  FROM FCT_CONSIGNMENT_INVENTORY fci"
//				+ "  WHERE fci.ACCOUNT_ID=?1").toString();
		//PAG_SELECT_COUNT_CONS_INV_BY_ACC_ID
//		String countQuery = new StringBuilder().append("SELECT COUNT(*) "
//				+ "  FROM FCT_CONSIGNMENT_INVENTORY fci"
//				+ "  WHERE fci.ACCOUNT_ID=?1").toString();
		
		Query query = (Query) entityManager.createNativeQuery(QueryConstant.PAG_SELECT_FCT_CONS_INV
				+ QueryConstant.PAG_WHERE_FCT_CONS_INV).setParameter(1,accountId);
		Query queryCount = (Query) entityManager.createNativeQuery(QueryConstant.PAG_SELECT_COUNT_FCT_CONS_INV 
			+QueryConstant.PAG_WHERE_FCT_CONS_INV ).setParameter(1,accountId);
		int pageNumber = pageRequest.getPageNumber();
		int pageSize = pageRequest.getPageSize();
		query.setFirstResult((pageNumber) * pageSize);
		query.setMaxResults(pageSize);
		
		

		List<Object[]> queryResult = query.getResultList();
		List<Object> countResult = queryCount.getResultList();
		int totalElements = 0;
		if (!countResult.isEmpty()) {
			totalElements = (int) countResult.get(0);   
		}

		List<ConsignInventoryResponse> consignInventoryResponses = queryResult.stream().map(this::mapToObjectArrayConsInvtResponse)
				.collect(Collectors.toList());

		Page<ConsignInventoryResponse> pageOfConsignInvtResp = new PageImpl<>(consignInventoryResponses, pageRequest, totalElements);

		PageResponseDTO pageResponse = new PageResponseDTO();
		pageResponse.setData(pageOfConsignInvtResp.getContent());
		pageResponse.setFirst(pageOfConsignInvtResp.isFirst());
		pageResponse.setLast(pageOfConsignInvtResp.isLast());
		pageResponse.setPageNumber(pageOfConsignInvtResp.getNumber());
		pageResponse.setRecordCount(pageOfConsignInvtResp.getNumberOfElements());
		pageResponse.setRecordOffset(pageOfConsignInvtResp.getPageable().getOffset());
		pageResponse.setRequestedCount(pageOfConsignInvtResp.getSize());
		pageResponse.setTotalPages(pageOfConsignInvtResp.getTotalPages());
		pageResponse.setTotalRecords(pageOfConsignInvtResp.getTotalElements());
		return pageResponse;
	}

	@Override
	public PageResponseDTO getAllConsignInvent(PageRequest pageRequest) {
		
//		String selectQuery = new StringBuilder().append("SELECT fci.ACCOUNT_ID,\r\n"
//				+ "      fci.TERRITORY_ID\r\n"
//				+ "      ,fci.ITEM_ID\r\n"
//				+ "      ,fci.Batch\r\n"
//				+ "      ,fci.Expiry_Date\r\n"
//				+ "      ,fci.Total_Stock\r\n"
//				+ "      ,fci.MATERIAL_KEY\r\n"
//				+ "      ,fci.CUSTOMER\r\n"
//				+ "      ,fci.CUSTOMER_NAME\r\n"
//				+ "      ,fci.RFRSH_DATE\r\n"
//				+ "  FROM FCT_CONSIGNMENT_INVENTORY fci").toString();
		
//		String countQuery = new StringBuilder().append("SELECT COUNT(*) "
//				+ "  FROM FCT_CONSIGNMENT_INVENTORY fci").toString();
		
		Query query = (Query) entityManager.createNativeQuery(QueryConstant.PAG_SELECT_FCT_CONS_INV);
		Query queryCount = (Query) entityManager.createNativeQuery(QueryConstant.PAG_SELECT_COUNT_FCT_CONS_INV);
		int pageNumber = pageRequest.getPageNumber();
		int pageSize = pageRequest.getPageSize();
		query.setFirstResult((pageNumber) * pageSize);
		query.setMaxResults(pageSize);
	

		List<Object[]> queryResult = query.getResultList();
		List<Object> countResult = queryCount.getResultList();
		int totalElements = 0;
		if (!countResult.isEmpty()) {
			totalElements = (int) countResult.get(0);   
		}

		List<ConsignInventoryResponse> consignInventoryResponses = queryResult.stream().map(this::mapToObjectArrayConsInvtResponse)
				.collect(Collectors.toList());

		Page<ConsignInventoryResponse> pageOfConsignInvtResp = new PageImpl<>(consignInventoryResponses, pageRequest, totalElements);

		PageResponseDTO pageResponse = new PageResponseDTO();
		pageResponse.setData(pageOfConsignInvtResp.getContent());
		pageResponse.setFirst(pageOfConsignInvtResp.isFirst());
		pageResponse.setLast(pageOfConsignInvtResp.isLast());
		pageResponse.setPageNumber(pageOfConsignInvtResp.getNumber());
		pageResponse.setRecordCount(pageOfConsignInvtResp.getNumberOfElements());
		pageResponse.setRecordOffset(pageOfConsignInvtResp.getPageable().getOffset());
		pageResponse.setRequestedCount(pageOfConsignInvtResp.getSize());
		pageResponse.setTotalPages(pageOfConsignInvtResp.getTotalPages());
		pageResponse.setTotalRecords(pageOfConsignInvtResp.getTotalElements());
		return pageResponse;
	}
	
	private ConsignInventoryResponse mapToObjectArrayConsInvtResponse(Object[] record) {
		ConsignInventoryResponse response = new ConsignInventoryResponse();
		response.setMonthId((Integer) record[0]);
		response.setAccountId((Integer) record[1]);
		response.setTerritoryId((Integer) record[2]);
		response.setItemId((Integer) record[3]);
		response.setBatch((String) record[4]);
		Date expirydate = (Date) record[5];
		if(expirydate!=null)
		  response.setExpiryDate(expirydate.toLocalDate());
		response.setTotalStock((BigDecimal) record[6]);
		response.setMaterialKey((String) record[7]);
		response.setCustomer((String) record[8]);
		response.setCustomerName((String) record[9]);
		response.setLotNo((String) record[10]);
		Timestamp rfrshDate = (Timestamp) record[11];
		if(rfrshDate!=null)
		  response.setRfrshDate(rfrshDate.toLocalDateTime());
		response.setTrnInvRecId((Integer) record[12]);
		response.setCreatedUser((String) record[13]);
		Timestamp createdDate = (Timestamp) record[14];
		if(createdDate!=null)
		  response.setCreatedDate(createdDate.toLocalDateTime());
		
		return response;
	}
	
	@Override
	public List<ConsignInventoryResponse> getAllConsignmentList() {
		
		    Query nativeQuery = entityManager.createNativeQuery(QueryConstant.GET_ALL_FCT_CONSIGNMENT_INVENTORY);
		    List<Object[]> queryResult = nativeQuery.getResultList();
		    List<ConsignInventoryResponse> consignInventoryResponse = queryResult.stream()
		            .map(this::mapToObjectArrayConsInvtResponse).collect(Collectors.toList());
		    return consignInventoryResponse;
		}

	@Override
	public Long getTotalConsignInventoryCount() {
		  Query query = entityManager.createNativeQuery(QueryConstant.TOTAL_ACC_ID_COUNT_FCT_CONSIGNMENT_INVENTORY);
		  return ((Number) query.getSingleResult()).longValue(); 
	}

	
}
