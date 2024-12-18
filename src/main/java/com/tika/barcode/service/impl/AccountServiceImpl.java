package com.tika.barcode.service.impl;

import java.math.BigDecimal;


import java.sql.Date;
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

import jakarta.persistence.*;

import com.tika.barcode.constants.QueryConstant;


@Service
public class AccountServiceImpl implements AccountService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<AccountResponse> getAccountList(String user) {

//		List<Object[]> queryResult = entityManager.createNativeQuery("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,"
//				+ "b.ADDRESS2,b.CITY,b.[STATE],b.ZIP,\r\n" + "c.TERRITORY_ID,c.TERRITORY_CD,c.TERRITORY_NAME\r\n"
//				+ "from DIM_ACCOUNT b \r\n" + "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID)\r\n"
//				+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID)").getResultList();
//		List<Object[]> queryResult = entityManager.createNativeQuery("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,b.ADDRESS2,b.CITY,b.[STATE],b.ZIP,\r\n"
//				+ "c.TERRITORY_ID,c.TERRITORY_CD,c.TERRITORY_NAME\r\n"
//				+ "from DIM_ACCOUNT b \r\n"
//				+ "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID)\r\n"
//				+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID)\r\n"
//				+ "where c.TERRITORY_ID=943").getResultList();
		//SELECT_ACC_LIST_BY_USERNAME
//		List<Object[]> queryResult = entityManager.createNativeQuery("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,"
//				+ "b.ADDRESS2,b.CITY,b.[STATE],b.ZIP,\r\n"
//				+ "c.TERRITORY_ID,c.TERRITORY_CD,c.TERRITORY_NAME,\r\n"
//				+ "concat(FIRST_NAME,' ',LAST_NAME) REP_NAME,u.USER_NAME USER_LOGIN \r\n"
//				+ "from DIM_ACCOUNT b \r\n"
//				+ "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID)\r\n"
//				+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID)\r\n"
//				+ "left join DIM_USER u on (c.USER_ID=u.USER_ID)\r\n"
//				+ "where u.USER_NAME=?1").setParameter(1, user).getResultList();
		List<Object[]> queryResult = entityManager.createNativeQuery(QueryConstant.SELECT_ACC_LIST_BY_USERNAME).setParameter(1, user).getResultList();

		List<AccountResponse> accountResponses = queryResult.stream().map(this::mapToObjectArrayAccResponse)
				.collect(Collectors.toList());

		return accountResponses;
	}

	@Override
	public List<AcoountDetailsResponse> getAllAccountDetails(String user) {
//		List<Object[]> queryResult = entityManager
//				.createNativeQuery("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,b.ADDRESS2,"
//						+ "b.CITY,b.STATE,b.ZIP,\r\n"
//						+ "d.ITEM_ID,d.ITEM_NUMBER MATERIAL_KEY,d.ITEM_DESC1,a.Batch,a.LOT_NO,a.Expiry_Date,"
//						+ "a.Total_Stock QTY_IN_HAND\r\n" + "from FCT_CONSIGNMENT_INVENTORY a \r\n"
//						+ "join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
//						+ "join DIM_TERRITORY c on (a.TERRITORY_ID=c.TERRITORY_ID)\r\n"
//						+ "join DIM_ITEM d on (a.ITEM_ID=d.ITEM_ID)\r\n" )
//				.getResultList();
		//SELECT_ACC_DET_LIST_BY_USERNAME
		List<Object[]> queryResult = entityManager
				.createNativeQuery(QueryConstant.SELECT_ACC_DET_LIST_BY_USERNAME)
						//+ "where b.ACCOUNT_ID=883" )
				.setParameter(1, user).getResultList();

		List<AcoountDetailsResponse> acoountDetailsResponses = queryResult.stream()
				.map(this::mapToObjectArrayAccDetResponse).collect(Collectors.toList());
		
		return acoountDetailsResponses;
	}
		//SELECT_ACC_DET_BY_ACCID
	@Override
	public List<AcoountDetailsResponse> getAccountDetailsByAccId(Integer accountId) {
//		List<Object[]> queryResult = entityManager
//				.createNativeQuery("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,b.ADDRESS2,"
//						+ "b.CITY,b.STATE,b.ZIP,\r\n"
//						+ "d.ITEM_ID,d.ITEM_NUMBER MATERIAL_KEY,d.ITEM_DESC1,a.Batch,'AB1235' LOT_NO,a.Expiry_Date,"
//						+ "a.Total_Stock QTY_IN_HAND\r\n" + "from FCT_CONSIGNMENT_INVENTORY a \r\n"
//						+ "join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
//						+ "join DIM_TERRITORY c on (a.TERRITORY_ID=c.TERRITORY_ID)\r\n"
//						+ "join DIM_ITEM d on (a.ITEM_ID=d.ITEM_ID)\r\n" + "where b.ACCOUNT_ID=?1")
//				.setParameter(1, accountId).getResultList();
		List<Object[]> queryResult = entityManager
				.createNativeQuery(QueryConstant.SELECT_ACC_DET_BY_ACCID)
				.setParameter(1, accountId).getResultList();

		List<AcoountDetailsResponse> acoountDetailsResponses = queryResult.stream()
				.map(this::mapToObjectArrayAccDetResponse).collect(Collectors.toList());

		return acoountDetailsResponses;
	}

	@Override
	public PageResponseDTO getAccountListPagination(String accountName,PageRequest pageRequest) {
		
//		String selectQuery = new StringBuilder().append("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,"
//				+ "b.ADDRESS2,b.CITY,b.[STATE],b.ZIP,\r\n" + "c.TERRITORY_ID,c.TERRITORY_CD,c.TERRITORY_NAME\r\n"
//				+ "from DIM_ACCOUNT b \r\n" + "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID)\r\n"
//				+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID)").toString();
//		
//		String countQuery = new StringBuilder().append("select COUNT(*) "
//				+ "from DIM_ACCOUNT b \r\n" + "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID)\r\n"
//				+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID)").toString();
		
		//PAG_SELECT_ACC_LIST_BY_TER_ID_943
//		String selectQuery = new StringBuilder().append("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,b.ADDRESS2,b.CITY,b.[STATE],b.ZIP,"
//				+ "c.TERRITORY_ID,c.TERRITORY_CD,c.TERRITORY_NAME"
//				+ "from DIM_ACCOUNT b "
//				+ "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID) "
//				+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID) "
//				+ "where c.TERRITORY_ID=943").toString();
		
		//PAG_SELECT_COUNT_ACC_LIST_BY_TER_ID_943
//		String countQuery = new StringBuilder().append("select COUNT(*) "
//				+ "from DIM_ACCOUNT b "
//				+ "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID) "
//				+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID) "
//				+ "where c.TERRITORY_ID=943").toString();
		String selectQuery="";
		String countQuery="";
		
		if(accountName!=null) {
			if(!accountName.isEmpty()) {
			selectQuery = new StringBuilder().append(QueryConstant.PAG_SELECT_ACC_LIST_BY_TER_ID_943).append(QueryConstant.PAG_WHERE_ACC_NAME_LIKE).append("'%")
					.append(accountName).append("%'").toString();
			countQuery = new StringBuilder().append(QueryConstant.PAG_SELECT_COUNT_ACC_LIST_BY_TER_ID_943).append(QueryConstant.PAG_WHERE_ACC_NAME_LIKE).append("'%")
					.append(accountName).append("%'").toString();
			}
		}

//		Query query = (Query) entityManager.createNativeQuery("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,"
//				+ "b.ADDRESS2,b.CITY,b.[STATE],b.ZIP,\r\n" + "c.TERRITORY_ID,c.TERRITORY_CD,c.TERRITORY_NAME\r\n"
//				+ "from DIM_ACCOUNT b \r\n" + "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID)\r\n"
//				+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID)");
//		
//		Query queryCount = (Query) entityManager.createNativeQuery("select COUNT(*) "
//				+ "from DIM_ACCOUNT b \r\n" + "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID)\r\n"
//				+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID)");
		
		Query query = (Query) entityManager.createNativeQuery(selectQuery);
		Query queryCount = (Query) entityManager.createNativeQuery(countQuery);
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
		//PAG_SELECT_ACC_DET_BY_ACC_ID
//		Query query = (Query) entityManager
//				.createNativeQuery("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,b.ADDRESS2,"
//						+ "b.CITY,b.STATE,b.ZIP,\r\n"
//						+ "d.ITEM_ID,d.ITEM_NUMBER MATERIAL_KEY,d.ITEM_DESC1,a.Batch,'AB1235' LOT_NO,a.Expiry_Date,"
//						+ "a.Total_Stock QTY_IN_HAND\r\n" + "from FCT_CONSIGNMENT_INVENTORY a \r\n"
//						+ "join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
//						+ "join DIM_TERRITORY c on (a.TERRITORY_ID=c.TERRITORY_ID)\r\n"
//						+ "join DIM_ITEM d on (a.ITEM_ID=d.ITEM_ID)\r\n" + "where b.ACCOUNT_ID=?1")
//				.setParameter(1, accountId);
		Query query = (Query) entityManager
				.createNativeQuery(QueryConstant.PAG_SELECT_ACC_DET_BY_ACC_ID)
				.setParameter(1, accountId);
		//PAG_SELECT_COUNT_ACC_DET_BY_ACC_ID
//		Query queryCount = (Query) entityManager
//				.createNativeQuery("select COUNT(*) from FCT_CONSIGNMENT_INVENTORY a \r\n"
//						+ "join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
//						+ "join DIM_TERRITORY c on (a.TERRITORY_ID=c.TERRITORY_ID)\r\n"
//						+ "join DIM_ITEM d on (a.ITEM_ID=d.ITEM_ID)\r\n" + "where b.ACCOUNT_ID=?1")
//				.setParameter(1, accountId);
		Query queryCount = (Query) entityManager
				.createNativeQuery(QueryConstant.PAG_SELECT_COUNT_ACC_DET_BY_ACC_ID)
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
		response.setRepName((String) record[10]);
		response.setUserName((String) record[11]);
		response.setAccountType((String) record[12]);
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
		response.setItemNumber((String) record[8]);
		response.setItemName((String) record[9]);
		//response.setBatch((String) record[10]);
		response.setLotNo((String) record[11]);
		Date expirydate = (Date) record[12];
		if(expirydate!=null)
		  response.setExpiryDate(expirydate.toLocalDate());
		response.setQtyInHand((BigDecimal) record[13]);
		response.setRepName((String) record[14]);
		response.setUserName((String) record[15]);

		return response;
	}

	
}
