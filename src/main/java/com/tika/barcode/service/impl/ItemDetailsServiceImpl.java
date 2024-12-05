package com.tika.barcode.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


import java.util.stream.Collectors;



import org.springframework.stereotype.Service;

import com.tika.barcode.dto.request.UpdateItemDetailsRequest;
import com.tika.barcode.dto.response.ItemDetailsResponse;
import com.tika.barcode.dto.response.ItemListResponse;
import com.tika.barcode.service.ItemDetailsService;

import jakarta.persistence.*;

import com.tika.barcode.constants.ParameterConstant;
import com.tika.barcode.constants.ProcedureConstant;
import com.tika.barcode.constants.QueryConstant;

@Service
public class ItemDetailsServiceImpl implements ItemDetailsService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ItemDetailsResponse> getItemDetails() {
		//SELECT_ITEM_DETAILS_DIM_ITEM
		List<Object[]> queryResult = entityManager.createNativeQuery(QueryConstant.SELECT_ITEM_DETAILS_DIM_ITEM).getResultList();

		List<ItemDetailsResponse> itemDetailsResponse = queryResult.stream().map(this::mapToObjectArrayItemResponse)
				.collect(Collectors.toList());

		return itemDetailsResponse;
	}

	private ItemDetailsResponse mapToObjectArrayItemResponse(Object[] record) {
		ItemDetailsResponse response = new ItemDetailsResponse();
		response.setItemId((Integer) record[0]);
		response.setItemNumber((String) record[1]);
		response.setItemName((String) record[2]);

		return response;
	}

	@Override
	public List<ItemListResponse> getAllItemDetails() {
		
		    Query nativeQuery = entityManager.createNativeQuery(QueryConstant.GET_ALL_ITEM_DETAILS_DIM_ITEM);
		    List<Object[]> queryResult = nativeQuery.getResultList();
		    List<ItemListResponse> itemListResponse = queryResult.stream()
		            .map(this::mapToObjectArrayItemListResponse).collect(Collectors.toList());
		    return itemListResponse;
		}
	
	private ItemListResponse mapToObjectArrayItemListResponse(Object[] record) {
		ItemListResponse response = new ItemListResponse();
		response.setItemId((Integer) record[0]);
		response.setItemCode((String) record[1]);
		response.setItemNumber((String) record[2]);
		response.setItemDesc1((String) record[3]);
		response.setItemDesc2((String) record[4]);
		response.setItemType((String) record[5]);
		response.setProdLine((Integer) record[6]);
		response.setItemGroupId((Integer) record[7]);
		response.setItemGroupName((String) record[8]);
		response.setProductGroupId((Integer) record[9]);
		response.setProductGroupName((String) record[10]);
		response.setImplantType((String) record[11]);
		Timestamp rfrshDate = (Timestamp) record[12];
		if (rfrshDate != null)
			response.setRfrshDate(rfrshDate.toLocalDateTime());
		
		return response;
	}

	@Override
	public String updateItemDetails(Integer itemId, UpdateItemDetailsRequest updateItemDetails) {
		
	try {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(ProcedureConstant.USP_ITEM_DETAILS_UPDATE);
		
		query.registerStoredProcedureParameter(ParameterConstant.ITEM_ID, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ITEM_CODE, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ITEM_NUMBER, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ITEM_DESC1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ITEM_DESC2, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ITEM_TYPE, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.PROD_LINE, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ITEM_GROUP_ID, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ITEM_GROUP_NAME, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.PRODUCT_GROUP_ID, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.PRODUCT_GROUP_NAME, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.IMPLANT_TYPE, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.RFRSH_DATE, LocalDateTime.class, ParameterMode.IN);
		

		query.setParameter(ParameterConstant.ITEM_ID, itemId);
		query.setParameter(ParameterConstant.ITEM_CODE, updateItemDetails.getItemCode());
		query.setParameter(ParameterConstant.ITEM_NUMBER, updateItemDetails.getItemNumber());
		query.setParameter(ParameterConstant.ITEM_DESC1, updateItemDetails.getItemDesc1());
		query.setParameter(ParameterConstant.ITEM_DESC2, updateItemDetails.getItemDesc2());
		query.setParameter(ParameterConstant.ITEM_TYPE, updateItemDetails.getItemType());
		query.setParameter(ParameterConstant.PROD_LINE, updateItemDetails.getProdLine());
		query.setParameter(ParameterConstant.ITEM_GROUP_ID, updateItemDetails.getItemGroupId());
		query.setParameter(ParameterConstant.ITEM_GROUP_NAME, updateItemDetails.getItemGroupName());
		query.setParameter(ParameterConstant.PRODUCT_GROUP_ID, updateItemDetails.getProductGroupId());
		query.setParameter(ParameterConstant.PRODUCT_GROUP_NAME, updateItemDetails.getProductGroupName());
		query.setParameter(ParameterConstant.IMPLANT_TYPE, updateItemDetails.getImplantType());
		query.setParameter(ParameterConstant.RFRSH_DATE, updateItemDetails.getRfrshDate());
		
		query.execute();
		return "Item Details updated successfully";
	}catch(Exception e) {
		throw new RuntimeException(" item details update failed",e);
	}
	
	}

	@Override
	public Long getItemIdCount() {
		  Query query = entityManager.createNativeQuery(QueryConstant.SELECT_TOTAL_ITEM_ID_COUNT_DIM_ITEM);
		  return ((Number) query.getSingleResult()).longValue(); 
	}
		

}
