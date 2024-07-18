package com.tika.barcode.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tika.barcode.dto.response.AccountResponse;
import com.tika.barcode.dto.response.AcoountDetailsResponse;
import com.tika.barcode.dto.response.PageResponseDTO;
import com.tika.barcode.service.AccountService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class AccountServiceImpl implements AccountService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<AccountResponse> getAccountList() {

		List<Object[]> queryResult = entityManager.createNativeQuery("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,"
				+ "b.ADDRESS2,b.CITY,b.[STATE],b.ZIP,\r\n" + "c.TERRITORY_ID,c.TERRITORY_CD,c.TERRITORY_NAME\r\n"
				+ "from DIM_ACCOUNT b \r\n" + "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID)\r\n"
				+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID)").getResultList();

		List<AccountResponse> accountResponses = queryResult.stream().map(this::mapToObjectArrayAccResponse)
				.collect(Collectors.toList());

		return accountResponses;
	}

	@Override
	public List<AcoountDetailsResponse> getAccountDetailsByAccId(Integer accountId) {
		List<Object[]> queryResult = entityManager
				.createNativeQuery("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,b.ADDRESS2,"
						+ "b.CITY,b.STATE,b.ZIP,\r\n"
						+ "d.ITEM_ID,d.ITEM_NUMBER MATERIAL_KEY,d.ITEM_DESC1,a.Batch,'AB1235' LOT_NO,a.Expiry_Date,"
						+ "a.Total_Stock QTY_IN_HAND\r\n" + "from FCT_CONSIGNMENT_INVENTORY a \r\n"
						+ "join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
						+ "join DIM_TERRITORY c on (a.TERRITORY_ID=c.TERRITORY_ID)\r\n"
						+ "join DIM_ITEM d on (a.ITEM_ID=d.ITEM_ID)\r\n" + "where b.ACCOUNT_ID=?1")
				.setParameter(1, accountId).getResultList();

		List<AcoountDetailsResponse> acoountDetailsResponses = queryResult.stream()
				.map(this::mapToObjectArrayAccDetResponse).collect(Collectors.toList());

		return acoountDetailsResponses;
	}

	@Override
	public PageResponseDTO getAccountListPagination(PageRequest pageRequest) {

		Query query = (Query) entityManager.createNativeQuery("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,"
				+ "b.ADDRESS2,b.CITY,b.[STATE],b.ZIP,\r\n" + "c.TERRITORY_ID,c.TERRITORY_CD,c.TERRITORY_NAME\r\n"
				+ "from DIM_ACCOUNT b \r\n" + "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID)\r\n"
				+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID)");
		Query queryCount = (Query) entityManager.createNativeQuery("select COUNT(*) "
				+ "from DIM_ACCOUNT b \r\n" + "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID)\r\n"
				+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID)");
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

		List<AccountResponse> accountResponses = queryResult.stream().map(this::mapToObjectArrayAccResponse)
				.collect(Collectors.toList());

		Page<AccountResponse> pageOfAccountResponse = new PageImpl<>(accountResponses, pageRequest, totalElements);

		PageResponseDTO pageResponse = new PageResponseDTO();
		pageResponse.setData(pageOfAccountResponse.getContent());
		pageResponse.setFirst(pageOfAccountResponse.isFirst());
		pageResponse.setLast(pageOfAccountResponse.isLast());
		pageResponse.setPageNumber(pageOfAccountResponse.getNumber());
		pageResponse.setRecordCount(pageOfAccountResponse.getNumberOfElements());
		pageResponse.setRecordOffset(pageOfAccountResponse.getPageable().getOffset());
		pageResponse.setRequestedCount(pageOfAccountResponse.getSize());
		pageResponse.setTotalPages(pageOfAccountResponse.getTotalPages());
		pageResponse.setTotalRecords(pageOfAccountResponse.getTotalElements());
		return pageResponse;
	}

	@Override
	public PageResponseDTO getAccountDetailsByAccIdPagination(Integer accountId, PageRequest pageRequest) {
		Query query = (Query) entityManager
				.createNativeQuery("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,b.ADDRESS2,"
						+ "b.CITY,b.STATE,b.ZIP,\r\n"
						+ "d.ITEM_ID,d.ITEM_NUMBER MATERIAL_KEY,d.ITEM_DESC1,a.Batch,'AB1235' LOT_NO,a.Expiry_Date,"
						+ "a.Total_Stock QTY_IN_HAND\r\n" + "from FCT_CONSIGNMENT_INVENTORY a \r\n"
						+ "join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
						+ "join DIM_TERRITORY c on (a.TERRITORY_ID=c.TERRITORY_ID)\r\n"
						+ "join DIM_ITEM d on (a.ITEM_ID=d.ITEM_ID)\r\n" + "where b.ACCOUNT_ID=?1")
				.setParameter(1, accountId);
		Query queryCount = (Query) entityManager
				.createNativeQuery("select COUNT(*) from FCT_CONSIGNMENT_INVENTORY a \r\n"
						+ "join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
						+ "join DIM_TERRITORY c on (a.TERRITORY_ID=c.TERRITORY_ID)\r\n"
						+ "join DIM_ITEM d on (a.ITEM_ID=d.ITEM_ID)\r\n" + "where b.ACCOUNT_ID=?1")
				.setParameter(1, accountId);
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

		List<AcoountDetailsResponse> acoountDetailsResponse = queryResult.stream()
				.map(this::mapToObjectArrayAccDetResponse).collect(Collectors.toList());

		Page<AcoountDetailsResponse> pageOfAcoountDetailsResponse = new PageImpl<>(acoountDetailsResponse, pageRequest,
				totalElements);

		PageResponseDTO pageResponse = new PageResponseDTO();
		pageResponse.setData(pageOfAcoountDetailsResponse.getContent());
		pageResponse.setFirst(pageOfAcoountDetailsResponse.isFirst());
		pageResponse.setLast(pageOfAcoountDetailsResponse.isLast());
		pageResponse.setPageNumber(pageOfAcoountDetailsResponse.getNumber());
		pageResponse.setRecordCount(pageOfAcoountDetailsResponse.getNumberOfElements());
		pageResponse.setRecordOffset(pageOfAcoountDetailsResponse.getPageable().getOffset());
		pageResponse.setRequestedCount(pageOfAcoountDetailsResponse.getSize());
		pageResponse.setTotalPages(pageOfAcoountDetailsResponse.getTotalPages());
		pageResponse.setTotalRecords(pageOfAcoountDetailsResponse.getTotalElements());
		return pageResponse;
	}

	private AccountResponse mapToObjectArrayAccResponse(Object[] record) {
		AccountResponse response = new AccountResponse();
		response.setAccountId((Integer) record[0]);
		response.setAccountName((String) record[1]);
		response.setAddress1((String) record[2]);
		response.setAddress2((String) record[3]);
		response.setCity((String) record[4]);
		response.setState((String) record[5]);
		response.setZip((String) record[6]);
		response.setTerritoryId((Integer) record[7]);
		response.setTerritoryCd((String) record[8]);
		response.setTerritoryName((String) record[9]);
		return response;
	}

	private AcoountDetailsResponse mapToObjectArrayAccDetResponse(Object[] record) {
		AcoountDetailsResponse response = new AcoountDetailsResponse();
		response.setAccountId((Integer) record[0]);
		response.setAccountName((String) record[1]);
		response.setAddress1((String) record[2]);
		response.setAddress2((String) record[3]);
		response.setCity((String) record[4]);
		response.setState((String) record[5]);
		response.setZip((String) record[6]);
		response.setItemId((Integer) record[7]);
		response.setMaterialKey((String) record[8]);
		response.setItemDesc1((String) record[9]);
		response.setBatch((String) record[10]);
		response.setLotNo((String) record[11]);
		response.setExpiryDate((Date) record[12]);
		response.setQtyInHand((BigDecimal) record[13]);

		return response;
	}

}
