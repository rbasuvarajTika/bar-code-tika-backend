package com.tika.barcode.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tika.barcode.constants.QueryConstant;
import com.tika.barcode.dto.response.ConsignInventoryResponse;
import com.tika.barcode.dto.response.PageResponseDTO;
import com.tika.barcode.service.ConsignInventoryService;

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
		response.setAccountId((Integer) record[0]);
		response.setTerritoryId((Integer) record[1]);
		response.setItemId((Integer) record[2]);
		response.setBatchNo((String) record[3]);
		Date expirydate = (Date) record[4];
		if(expirydate!=null)
		  response.setExpiryDate(expirydate.toLocalDate());
		response.setTotalStock((BigDecimal) record[5]);
		response.setMaterialKey((String) record[6]);
		response.setCustomerId((String) record[7]);
		response.setCustomerName((String) record[8]);
		Timestamp rfrshDate = (Timestamp) record[9];
		if(rfrshDate!=null)
		  response.setRfrshDate(rfrshDate.toLocalDateTime());
		return response;
	}


}
