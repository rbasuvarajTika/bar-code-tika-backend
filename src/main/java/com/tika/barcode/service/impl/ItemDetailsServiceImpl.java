package com.tika.barcode.service.impl;

import java.util.List;

import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.tika.barcode.dto.response.ItemDetailsResponse;
import com.tika.barcode.service.ItemDetailsService;
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
}
