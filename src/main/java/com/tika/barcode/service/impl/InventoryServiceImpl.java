package com.tika.barcode.service.impl;

import java.io.ByteArrayOutputStream;



import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.tika.barcode.constants.ParameterConstant;
import com.tika.barcode.constants.ProcedureConstant;
import com.tika.barcode.constants.QueryConstant;
import com.tika.barcode.dto.request.AddInventoryRequest;
import com.tika.barcode.dto.request.AddNotificationTranRequest;
import com.tika.barcode.dto.request.CloseInventoryRequest;
import com.tika.barcode.dto.request.InitiateInventoryRequest;
import com.tika.barcode.dto.request.InventoryReconRequest;
import com.tika.barcode.dto.request.ModifyInventoryRequest;
import com.tika.barcode.dto.request.SubmitInventoryRequest;
import com.tika.barcode.dto.response.AcoountDetailsResponse;
import com.tika.barcode.dto.response.InventoryRecCloseDetailResponse;
import com.tika.barcode.dto.response.InventoryRecClosePdfResponse;
import com.tika.barcode.dto.response.InventoryFctPdfResponse;
import com.tika.barcode.dto.response.InventoryRecDetailResponse;
import com.tika.barcode.dto.response.InventoryReconResponse;
import com.tika.barcode.dto.response.InventoryReconcileCollectiveResponse;

import com.tika.barcode.dto.response.NotificationConfEmailResponse;
import com.tika.barcode.dto.response.PageResponseDTO;
import com.tika.barcode.dto.response.TerrAlignmentResponse;
import com.tika.barcode.dto.response.TerritoryResponse;
import com.tika.barcode.service.InventoryService;
import com.tika.barcode.service.NotificationConfigService;
import com.tika.barcode.service.NotificationTranService;
import com.tika.barcode.utility.EmailService;
import com.tika.barcode.utility.PdfGenerator;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

@Service
public class InventoryServiceImpl implements InventoryService {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private PdfGenerator pdfGenerator;
	@Autowired
	EmailService emailService;
	@Autowired
	NotificationConfigService notificationConfigService;
	@Autowired
	NotificationTranService notificationTranService;

	@Override
	public Integer initiateInventoryProc(InitiateInventoryRequest initiateInventoryRequest) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(ProcedureConstant.USP_INVENTORY_INITIATE);
		query.registerStoredProcedureParameter(ParameterConstant.USER, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ACCOUNT_ID, Integer.class, ParameterMode.IN);

		query.setParameter(ParameterConstant.USER, initiateInventoryRequest.getUser());
		query.setParameter(ParameterConstant.ACCOUNT_ID, initiateInventoryRequest.getAccountId());

		query.execute();
		// Retrieve the output parameter
		Integer newPrimaryKey = retrievePrimaryKey(initiateInventoryRequest);

		return newPrimaryKey;
	}

	@Override
	public void addInventoryProc(AddInventoryRequest addInventoryRequest) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(ProcedureConstant.USP_INVENTORY_ADD);
		query.registerStoredProcedureParameter(ParameterConstant.USER, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.TRN_INV_REC_ID, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ITEM_NUMBER, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.BATCH_NO, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.LOT_NO, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.QTY_IN_HAND, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.EXPIRY_DATE, Date.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ACCOUNT_ID, Integer.class, ParameterMode.IN);

		query.setParameter(ParameterConstant.USER, addInventoryRequest.getUser());
		query.setParameter(ParameterConstant.TRN_INV_REC_ID, addInventoryRequest.getTrnInvRecId());
		query.setParameter(ParameterConstant.ITEM_NUMBER, addInventoryRequest.getItemNumber());
		query.setParameter(ParameterConstant.BATCH_NO, addInventoryRequest.getBatchNo());
		query.setParameter(ParameterConstant.LOT_NO, addInventoryRequest.getLotNo());
		query.setParameter(ParameterConstant.QTY_IN_HAND, addInventoryRequest.getQtyInHand());
		query.setParameter(ParameterConstant.EXPIRY_DATE, addInventoryRequest.getExpiryDate());
		query.setParameter(ParameterConstant.ACCOUNT_ID, addInventoryRequest.getAccountId());

		query.execute();
	}

	@Override
	public String modifyInventoryProc(ModifyInventoryRequest modifyInventoryRequest) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(ProcedureConstant.USP_INVENTORY_MOD);
		query.registerStoredProcedureParameter(ParameterConstant.USER, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.TRN_INV_REC_ID, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.ITEM_NUMBER, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.LOT_NO, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.EXPIRY_DATE, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.QTY_IN_HAND, Integer.class, ParameterMode.IN);

		query.setParameter(ParameterConstant.USER, modifyInventoryRequest.getUser());
		query.setParameter(ParameterConstant.TRN_INV_REC_ID, modifyInventoryRequest.getTrnInvRecDetailId());
		query.setParameter(ParameterConstant.ITEM_NUMBER, modifyInventoryRequest.getItemNumber());
		query.setParameter(ParameterConstant.LOT_NO, modifyInventoryRequest.getLotNo());
		query.setParameter(ParameterConstant.EXPIRY_DATE, modifyInventoryRequest.getExpiryDate());
		query.setParameter(ParameterConstant.QTY_IN_HAND, modifyInventoryRequest.getQtyInHand());

		query.execute();

		return "inventory modified successfully";
	}

	@Override
	public String submitInventoryProc(SubmitInventoryRequest submitInventoryRequest) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(ProcedureConstant.USP_INVENTORY_SUBMIT);
		query.registerStoredProcedureParameter(ParameterConstant.USER, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.TRN_INV_REC_ID, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.RECON_NOTES, String.class, ParameterMode.IN);

		query.setParameter(ParameterConstant.USER, submitInventoryRequest.getUser());
		query.setParameter(ParameterConstant.TRN_INV_REC_ID, submitInventoryRequest.getTrnInvRecId());
		query.setParameter(ParameterConstant.RECON_NOTES, submitInventoryRequest.getReconNotes());

		query.execute();

		return "inventory submitted successfully";
	}

	@Override
	public String insertInventoryRecon(List<InventoryReconRequest> inventoryReconRequest) {
		InventoryReconRequest getInvRec = inventoryReconRequest.get(0);
		//CHECK_INVREC__INITIATE_INCERT_REQ
		//String checkQuery = " select a.TRN_INV_REC_ID \r\n" + "from TRN_INVENTORY_RECONCILE a \r\n"
		//		+ "where a.ACCOUNT_ID=?1 and a.CREATED_USER=?2 and a.RECON_STATUS=?3 ";
		@SuppressWarnings("unchecked")
		List<Object> queryResult = entityManager.createNativeQuery(QueryConstant.CHECK_INVREC_INITIATE_INCERT_REQ).setParameter(1, getInvRec.getAccountId())
				.setParameter(2, getInvRec.getUser()).setParameter(3, "Submit").getResultList();
		String response = "";
		Integer trnInvRecId = 0;
		// First Condition Null
		if (queryResult.isEmpty()) {
			InitiateInventoryRequest inventoryRequest = new InitiateInventoryRequest(getInvRec.getUser(),
					getInvRec.getAccountId());
			trnInvRecId = initiateInventoryProc(inventoryRequest);
		} else {
			trnInvRecId = (Integer) queryResult.get(0);
		}

		for (InventoryReconRequest request : inventoryReconRequest) {

			response = insertInventoryRecon(request, trnInvRecId);

		}
		return response;

	}

	private String insertInventoryRecon(InventoryReconRequest inventoryReconRequest, Integer invRecId) {
		String submitResponse = "";

		@SuppressWarnings("unchecked")
		//CHECK_INVREC_DET__ADD_OR_MOD_REQ
		/*List<Object> queryResult = entityManager.createNativeQuery("select a.TRN_INV_REC_DETAIL_ID "
				+ "from TRN_INVENTORY_RECONCILE_DETAIL a \r\n"
				+ "where a.ITEM_CODE=?1 AND a.LOT_NO=?2 AND a.BATCH_NO=?3 AND a.EXPIRY_DATE=?4 AND a.QTY_IN_HAND=?5")*/
		List<Object> queryResult = entityManager.createNativeQuery(QueryConstant.CHECK_INVREC_DET__ADD_OR_MOD_REQ)
				.setParameter(1, inventoryReconRequest.getItemNumber())
				.setParameter(2, inventoryReconRequest.getLotNo()).setParameter(3, inventoryReconRequest.getBatchno())
				.setParameter(4, inventoryReconRequest.getExpiryDate())
				.setParameter(5, inventoryReconRequest.getQtyInHand()).getResultList();

		if (queryResult.isEmpty()) {
			AddInventoryRequest addInventoryRequest = new AddInventoryRequest();
			addInventoryRequest.setUser(inventoryReconRequest.getUser());
			addInventoryRequest.setTrnInvRecId(invRecId);
			addInventoryRequest.setItemNumber(inventoryReconRequest.getItemNumber());
			if (inventoryReconRequest.getBatchno() == null) {
				addInventoryRequest.setBatchNo("NA");
			} else {
				addInventoryRequest.setBatchNo(inventoryReconRequest.getBatchno());
			}
			addInventoryRequest.setLotNo(inventoryReconRequest.getLotNo());
			addInventoryRequest.setQtyInHand(inventoryReconRequest.getQtyInHand());
			// String dateString = "2024-08-12";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = new java.util.Date();

			try {
				date = dateFormat.parse(inventoryReconRequest.getExpiryDate());
				System.out.println("Date: " + date);
			} catch (ParseException | java.text.ParseException e) {
				e.printStackTrace();
			}
			addInventoryRequest.setExpiryDate(date);
			addInventoryRequest.setAccountId(inventoryReconRequest.getAccountId());
		    addInventoryProc(addInventoryRequest);
		} else {
			Integer trnInvRecDetId = (Integer) queryResult.get(0);
			ModifyInventoryRequest mapMod = new ModifyInventoryRequest();
			mapMod.setUser(inventoryReconRequest.getUser());
			mapMod.setTrnInvRecDetailId(trnInvRecDetId);
			mapMod.setItemNumber(inventoryReconRequest.getItemNumber());
			mapMod.setQtyInHand(inventoryReconRequest.getQtyInHand());
			@SuppressWarnings("unused")
			String modifyResponse = modifyInventoryProc(mapMod);
		}
		SubmitInventoryRequest submitInventoryRequest = new SubmitInventoryRequest(inventoryReconRequest.getUser(),
				invRecId, inventoryReconRequest.getReconNotes());
		submitResponse = submitInventoryProc(submitInventoryRequest);

		return submitResponse;
	}

	private Integer retrievePrimaryKey(InitiateInventoryRequest initiateInventoryRequest) {
		// Assuming there is a CreatedAt timestamp or an auto-incrementing ID field
		// RETRIVE_INVREC_INITIATE_PK
//		String sql = "select TOP 1 a.TRN_INV_REC_ID from TRN_INVENTORY_RECONCILE a "
//				+ "	where a.ACCOUNT_ID=?1 AND a.CREATED_USER=?2  ORDER BY a.CREATED_DATE DESC ";

		// If no CreatedAt timestamp, use the auto-incrementing primary key
		// String sql = "SELECT id FROM Inventory WHERE User = :user AND AccountId =
		// :accountId ORDER BY id DESC LIMIT 1";

		Query nativeQuery = entityManager.createNativeQuery(QueryConstant.RETRIVE_INVREC_INITIATE_PK);
		nativeQuery.setParameter(1, initiateInventoryRequest.getAccountId());
		nativeQuery.setParameter(2, initiateInventoryRequest.getUser());

		// Execute the query and retrieve the primary key
		Integer primaryKey = (Integer) nativeQuery.getSingleResult();

		return primaryKey;
	}

	private Integer retrievePrimaryKey(AddInventoryRequest addInventoryRequest) {
		// Assuming there is a CreatedAt timestamp or an auto-incrementing ID field
		// RETRIVE_INVREC_DET_ADD_PK
//		String sql = "select TOP 1 b.TRN_INV_REC_DETAIL_ID from TRN_INVENTORY_RECONCILE_DETAIL b "
//				+ "	where b.CREATED_USER=?1 AND b.TRN_INV_REC_ID=?2  ORDER BY b.CREATED_DATE DESC ";

		// If no CreatedAt timestamp, use the auto-incrementing primary key
		// String sql = "SELECT id FROM Inventory WHERE User = :user AND AccountId =
		// :accountId ORDER BY id DESC LIMIT 1";

		Query nativeQuery = entityManager.createNativeQuery(QueryConstant.RETRIVE_INVREC_DET_ADD_PK);
		nativeQuery.setParameter(1, addInventoryRequest.getUser());
		nativeQuery.setParameter(2, addInventoryRequest.getTrnInvRecId());

		// Execute the query and retrieve the primary key
		Integer primaryKey = (Integer) nativeQuery.getSingleResult();

		return primaryKey;
	}

	@Override
	public List<InventoryReconResponse> getInvRecByAccIdAndUser(String user) {
		//GET_INVREC_BY_ACCID_AND_USER
//		String sql = "select a.TRN_INV_REC_ID,a.ACCOUNT_ID,a.RECON_STATUS,a.RECON_NOTES,a.CREATED_USER,"
//				+ " a.CREATED_DATE,a.UPDATED_USER,a.UPDATED_DATE from TRN_INVENTORY_RECONCILE a "
//				+ "	where a.CREATED_USER=?1 ";
		Query nativeQuery = entityManager.createNativeQuery(QueryConstant.GET_INVREC_BY_ACCID_AND_USER);
		nativeQuery.setParameter(1, user);
		List<Object[]> queryResult = nativeQuery.getResultList();
		List<InventoryReconResponse> inventoryReconResponse = queryResult.stream()
				.map(this::mapToObjectArrayInvRecResponse).collect(Collectors.toList());
		return inventoryReconResponse;
	}

	private InventoryReconResponse mapToObjectArrayInvRecResponse(Object[] record) {
		InventoryReconResponse response = new InventoryReconResponse();
		response.setTrnInvRecId((Integer) record[0]);
		response.setAccountId((Integer) record[1]);
		response.setReconStatus((String) record[2]);
		response.setReconNotes((String) record[3]);
		response.setCreatedUser((String) record[4]);
		Timestamp createdDate = (Timestamp) record[5];
		if (createdDate != null)
			response.setCreatedDate(createdDate.toLocalDateTime());
		response.setUpdatedUser((String) record[6]);
		Timestamp updated = (Timestamp) record[7];
		if (updated != null)
			response.setUpdatedDate(updated.toLocalDateTime());

		return response;
	}

	@Override
	public List<InventoryRecDetailResponse> getInvRecDetails(String username) {
		//GET_INVREC_DET_BY_STATUS_CLOSED_AND_SUBMIT_AND_CREATED_USER
//		String sql = " select distinct b.ACCOUNT_ID,c.ACCOUNT_NAME,a.TRN_INV_REC_ID,"
//				+ "b.REC_CYCLE_ID,b.CREATED_DATE,b.RECON_CLOSED_DATE,b.RECON_STATUS\r\n"
//				+ "from TRN_INVENTORY_RECONCILE_DETAIL a \r\n"
//				+ "left join TRN_INVENTORY_RECONCILE b on (a.TRN_INV_REC_ID=b.TRN_INV_REC_ID)\r\n"
//				+ "join DIM_ACCOUNT c on (b.ACCOUNT_ID=c.ACCOUNT_ID)\r\n"
//				+ "where b.RECON_STATUS IN ('Submit','Closed') and a.CREATED_USER=?1";
		Query nativeQuery = entityManager.createNativeQuery(QueryConstant.GET_INVREC_DET_BY_STATUS_CLOSED_AND_SUBMIT_AND_CREATED_USER);
		nativeQuery.setParameter(1, username);
		List<Object[]> queryResult = nativeQuery.getResultList();
		List<InventoryRecDetailResponse> inventoryReconDetailResonses = queryResult.stream()
				.map(this::mapToObjectArrayInvRecDetResponse).collect(Collectors.toList());
		return inventoryReconDetailResonses;
	}

	@Override
	public List<InventoryRecCloseDetailResponse> getInvRecCloseDetails(String username, Integer trnInvRecId) {
		//GET_INVREC_DET_BY_STATUS_CLOSED_AND_CREATED_USER_AND_TRN_INVREC_ID
//		String sql = " select  b.ACCOUNT_ID,c.ACCOUNT_NAME,a.TRN_INV_REC_ID,a.TRN_INV_REC_DETAIL_ID,a.BATCH_NO,"
//				+ "a.ITEM_ID,a.ITEM_CODE,a.LOT_NO,b.REC_CYCLE_ID,b.CREATED_DATE,A.QTY_IN_HAND,B.RECON_CLOSED_DATE,b.RECON_STATUS\r\n"
//				+ "from TRN_INVENTORY_RECONCILE_DETAIL a \r\n"
//				+ "join TRN_INVENTORY_RECONCILE b on (a.TRN_INV_REC_ID=b.TRN_INV_REC_ID)\r\n"
//				+ "join DIM_ACCOUNT c on (b.ACCOUNT_ID=c.ACCOUNT_ID)\r\n"
//				+ "where b.RECON_STATUS IN ('Closed') and a.CREATED_USER=?1 and a.TRN_INV_REC_ID=?2";
		Query nativeQuery = entityManager.createNativeQuery(QueryConstant.SELECT_INREC_DET
				+ " where b.RECON_STATUS IN ('Closed') and a.CREATED_USER=?1 and a.TRN_INV_REC_ID=?2" );
		nativeQuery.setParameter(1, username);
		nativeQuery.setParameter(2, trnInvRecId);
		List<Object[]> queryResult = nativeQuery.getResultList();

		List<InventoryRecCloseDetailResponse> inventoryReconDetailResonses = queryResult.stream()
				.map(this::mapToObjectArrayInvRecDetCloseResponse).collect(Collectors.toList());
		return inventoryReconDetailResonses;
	}

	private InventoryRecDetailResponse mapToObjectArrayInvRecDetResponse(Object[] record) {
		InventoryRecDetailResponse response = new InventoryRecDetailResponse();
		response.setAccountId((Integer) record[0]);
		response.setAccountName((String) record[1]);
		response.setTrnInvRecId((Integer) record[2]);
		response.setReconCycleId((Integer) record[3]);
		Timestamp startDate = (Timestamp) record[4];
		if (startDate != null)
			response.setReconStartDate(startDate.toLocalDateTime());
		Date closedDate = (Date) record[5];
		if (closedDate != null)
			response.setReconClosedDate(closedDate.toLocalDate());
		response.setReconStatus((String) record[6]);
		return response;
	}

	private InventoryRecCloseDetailResponse mapToObjectArrayInvRecDetCloseResponse(Object[] record) {
		InventoryRecCloseDetailResponse response = new InventoryRecCloseDetailResponse();
		response.setAccountId((Integer) record[0]);
		response.setAccountName((String) record[1]);
		response.setTrnInvRecId((Integer) record[2]);
		response.setTrnInvRecDetailsId((Integer) record[3]);
		// response.setBatchNo((String) record[4]);
		response.setItemId((Integer) record[5]);
		response.setItemNumber((String) record[6]);
		response.setLotNo((String) record[7]);
		response.setReconCycleId((Integer) record[8]);
		Timestamp startDate = (Timestamp) record[9];
		if (startDate != null)
			response.setReconStartDate(startDate.toLocalDateTime());

		response.setQtyInHand((BigDecimal) record[10]);
		Date closedDate = (Date) record[11];
		if (closedDate != null)
			response.setReconClosedDate(closedDate.toLocalDate());
		response.setReconStatus((String) record[12]);
		return response;
	}

	@Override
	public String closeInventoryRecon(CloseInventoryRequest closeInventoryRequest) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(ProcedureConstant.USP_INVENTORY_CLOSE);
		query.registerStoredProcedureParameter(ParameterConstant.USER, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(ParameterConstant.TRN_INV_REC_ID, Integer.class, ParameterMode.IN);

		query.setParameter(ParameterConstant.USER, closeInventoryRequest.getUser());
		query.setParameter(ParameterConstant.TRN_INV_REC_ID, closeInventoryRequest.getTrnInvRecId());

		query.execute();

		return "inventory closed successfully";
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
		// response.setBatch((String) record[10]);
		response.setLotNo((String) record[11]);
		Date expirydate = (Date) record[12];
		if (expirydate != null)
			response.setExpiryDate(expirydate.toLocalDate());
		response.setQtyInHand((BigDecimal) record[13]);

		return response;
	}

	@Override
	public List<InventoryRecCloseDetailResponse> getInvRecCloseDetByTrnInvRecId(Integer trnInvRecId) {
		//GET_INVREC_DET_BY_STATUS_CLOSED_AND_TRN_INVREC_ID
		//SELECT_INREC_DET
		Query nativeQuery = entityManager.createNativeQuery(QueryConstant.SELECT_INREC_DET 
				+ " where b.RECON_STATUS IN ('Closed') AND a.TRN_INV_REC_ID =?1")
				.setParameter(1, trnInvRecId);
		List<Object[]> queryResult = nativeQuery.getResultList();
		List<InventoryRecCloseDetailResponse> inventoryReconDetailResonses = queryResult.stream()
				.map(this::mapToObjectArrayInvRecDetCloseResponse).collect(Collectors.toList());
		return inventoryReconDetailResonses;
	}

	private List<InventoryRecClosePdfResponse> getInvRecCloseDetById(Integer trnInvRecId) {
		//PDF_INVREC_DET_BY_STATUS_CLOSED_AND_TRN_INVREC_ID
//		String sql = " select  b.ACCOUNT_ID,c.ACCOUNT_NAME,\r\n"
//				+ "	a.ITEM_CODE,d.ITEM_DESC1, a.LOT_NO,b.CREATED_DATE,b.RECON_CLOSED_DATE,\r\n"
//				+ "	a.EXPIRY_DATE ,a.QTY_IN_HAND\r\n"
//				+ "	from TRN_INVENTORY_RECONCILE_DETAIL a \r\n"
//				+ "	join TRN_INVENTORY_RECONCILE b on (a.TRN_INV_REC_ID=b.TRN_INV_REC_ID)\r\n"
//				+ "	join DIM_ACCOUNT c on (b.ACCOUNT_ID=c.ACCOUNT_ID)\r\n"
//				+ "	left join DIM_ITEM d on (a.ITEM_ID = d.ITEM_ID)\r\n"
//				+ "	where b.RECON_STATUS IN ('Closed') AND a.TRN_INV_REC_ID =?1";
		Query nativeQuery = entityManager.createNativeQuery(QueryConstant.PDF_INVREC_DET_BY_STATUS_CLOSED_AND_TRN_INVREC_ID).setParameter(1, trnInvRecId);
		List<Object[]> queryResult = nativeQuery.getResultList();
		List<InventoryRecClosePdfResponse> inventoryRecClosePdfResponse = queryResult.stream()
				.map(this::mapToObjectArrayInvRecDetClosePdfResponse).collect(Collectors.toList());
		return inventoryRecClosePdfResponse;
	}
	
	private List<InventoryFctPdfResponse> getFctInventoryMissedDetails(Integer accountId) {
		Query nativeQuery = entityManager.createNativeQuery(QueryConstant.PDF_INVREC_MISSED_DETAILS_FCT).setParameter(1, accountId);
		List<Object[]> queryResult = nativeQuery.getResultList();
		List<InventoryFctPdfResponse> inventoryRecClosePdfResponse = queryResult.stream()
				.map(this::mapToObjectArrayInvFctClosePdfResponse).collect(Collectors.toList());
		return inventoryRecClosePdfResponse;
	}

	private InventoryRecClosePdfResponse mapToObjectArrayInvRecDetClosePdfResponse(Object[] record) {
		InventoryRecClosePdfResponse response = new InventoryRecClosePdfResponse();
		response.setAccountId((Integer) record[0]);
		response.setAccountName((String) record[1]);
		response.setItemNumber((String) record[2]);
		response.setItemName((String) record[3]);
		response.setLotNo((String) record[4]);
		Timestamp startDate = (Timestamp) record[5];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if (startDate != null)
			response.setReconStartDate(startDate.toLocalDateTime().format(formatter));
		Date closedDate = (Date) record[6];
		if (closedDate != null)
			response.setReconClosedDate(closedDate.toLocalDate());
		Date expiryDate = (Date) record[7];
		if (expiryDate != null)
			response.setExpiryDate(expiryDate.toLocalDate());
		response.setQtyInHand((BigDecimal) record[8]);
		return response;
	}
	
	private InventoryFctPdfResponse mapToObjectArrayInvFctClosePdfResponse(Object[] record) {
		InventoryFctPdfResponse response = new InventoryFctPdfResponse();
		response.setAccountId((Integer) record[0]);
		response.setAccountName((String) record[1]);
		response.setItemNumber((String) record[2]);
		response.setItemName((String) record[3]);
		response.setLotNo((String) record[4]);
		response.setTotalStock((BigDecimal) record[5]);
		return response;
	}

	public byte[] createInventoryPdf(Integer trnInvRecId) throws Exception {
		List<InventoryRecClosePdfResponse> details = getInvRecCloseDetById(trnInvRecId);
		HashMap<String, Object> data = new HashMap<>();
		data.put("details", details);
		if(details.size() > 0) {
			Integer accountId=details.get(0).getAccountId();
			List<InventoryFctPdfResponse> missedDetails = getFctInventoryMissedDetails(accountId);
			data.put("missedDetails", missedDetails);
		}
		
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		pdfGenerator.generatePdf("inventory-report", data, os);
		return os.toByteArray();
	}
	
	
	@Override
	public String getEmail(String user) {
		//	SELECT_MAIL_DIM_USER_BY_NAME

		@SuppressWarnings("unchecked")
//		List<Object> queryResult = entityManager.createNativeQuery("select USER_EMAIL \r\n"
//				+ "from DIM_USER \r\n"
//				+ "where USER_NAME=?1").setParameter(1, user).getResultList();
		List<Object> queryResult = entityManager.createNativeQuery(QueryConstant.SELECT_MAIL_DIM_USER_BY_NAME).setParameter(1, user).getResultList();
		if(queryResult.isEmpty()) {
			return "rbasuvaraj@tikamobile.com";
		}
		String email = (String) queryResult.get(0);
		return email;
	}

	@Override
	public String sendInventoryPdf(Integer trnInvRecId, String username) {
		NotificationConfEmailResponse confEmailResponse = new NotificationConfEmailResponse();
		String recipientEmail = "rbasuvaraj@tikamobile.com";
		String errorMsg = "NA";
		String deliveryStatus="success";
		//String user = "jsmith";
		try {
			
	    	if(username==null) {
	    		recipientEmail = "rbasuvaraj@tikamobile.com";
	    	}else {
	    		recipientEmail=getEmail(username);
	    	}
	    	recipientEmail = "rbasuvaraj@tikamobile.com";
	        byte[] pdfContent = createInventoryPdf(trnInvRecId);
	        confEmailResponse = notificationConfigService.getNotiConfByName(ParameterConstant.CLOSE_DETAILS_PDF);
	       // if(confEmailResponse!=null) {
				//String subject =  confEmailResponse.getNotificationSubject().replace("{USER_NAME}",request.getUser());
	        	String accountName = getAccountNameByInvRecId(trnInvRecId);
				String subject =  confEmailResponse.getNotificationSubject().replaceAll("\\{.*?\\}",accountName);
	        String fileName = "InventoryReport_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
	        emailService.sendEmailWithAttachment(recipientEmail, subject,confEmailResponse.getNotificationBody(), fileName, pdfContent);
	        
	       // }
	        return "send pdf to mail successfully";
		}catch(Exception e) {
			errorMsg = e.getMessage();
			deliveryStatus="failed";
			throw new RuntimeException("Error in send inevtory pdf",e);
		}finally {
			AddNotificationTranRequest addNotificationTranRequest = new AddNotificationTranRequest();
			addNotificationTranRequest.setNotificationId(confEmailResponse.getNotificationId());
			addNotificationTranRequest.setUser(username);
			addNotificationTranRequest.setUserEmail(recipientEmail);
			addNotificationTranRequest.setErrorMsg(errorMsg);
			addNotificationTranRequest.setDeliveryStatus(deliveryStatus);
			notificationTranService.addNotifficationTran(addNotificationTranRequest);
			
		}
			
	}

	private String getAccountNameByInvRecId(Integer trnInvRecId) {
	List<Object> queryResult = entityManager.createNativeQuery(QueryConstant.SELECT_INV_REC_BY_INV_REC_ID).setParameter(1, trnInvRecId).getResultList();
	String accountName = (String) queryResult.get(0).toString();
	return accountName;
	}
	
	
	@Override
	public List<InventoryReconcileCollectiveResponse> getAllCollectiveInventoryReconcile() {
	   Query nativeQuery = entityManager.createNativeQuery(QueryConstant.GEL_ALL_COLLECTIVE_TRN_RECONCILE_LIST);
	    List<Object[]> queryResult = nativeQuery.getResultList();
	    List<InventoryReconcileCollectiveResponse> inventoryReconcileCollectiveResponse = queryResult.stream()
	            .map(this::mapToObjectArrayInventoryReconcileCollectiveResponse).collect(Collectors.toList());
	    return inventoryReconcileCollectiveResponse;
	}
	
	
	private InventoryReconcileCollectiveResponse mapToObjectArrayInventoryReconcileCollectiveResponse(Object[] record) {
		InventoryReconcileCollectiveResponse response = new InventoryReconcileCollectiveResponse();
			response.setTrnInvRecId((Integer) record[0]);
			response.setTrnInvRecDetailId((Integer) record[1]);
			response.setAccountId((Integer) record[2]);
			response.setItemId((Integer) record[3]);
			response.setItemCode((String) record[4]);
			response.setBatchNo((String) record[5]);
			response.setLotNo((String) record[6]);
			Date expiryDate = (Date) record[7];
			if(expiryDate!=null)
			  response.setExpiryDate(expiryDate.toLocalDate());
			response.setQtyInHand((BigDecimal) record[8]);
			response.setMonthId((Integer) record[9]);
			response.setReconStatus((String) record[10]);
			response.setReconNotes((String) record[11]);
			response.setRecCycleId((Integer) record[12]);
			Date reconClosedDate = (Date) record[13];
			if(reconClosedDate!=null)
			  response.setReconClosedDate(reconClosedDate.toLocalDate());
			response.setCreatedUser((String) record[14]);
			Timestamp createdDate = (Timestamp) record[15];
			if(createdDate!=null)
			  response.setCreatedDate(createdDate.toLocalDateTime());
			response.setUpdatedUser((String) record[16]);
			Timestamp updatedDate = (Timestamp) record[17];
			if(updatedDate!=null)
			  response.setUpdatedDate(updatedDate.toLocalDateTime());

			return response;
		}

	
	@Override
    @Transactional
	public String updateInventoryDetails(Integer trnInvRecId, InventoryReconcileCollectiveResponse updateRequest) {
	    // Update TRN_INVENTORY_RECONCILE query
	    String updateReconcileQuery = "UPDATE TRN_INVENTORY_RECONCILE "
	            + "SET ACCOUNT_ID = :accountId, RECON_STATUS = :reconStatus, RECON_NOTES = :reconNotes, "
	            + "REC_CYCLE_ID = :recCycleId, RECON_CLOSED_DATE = :reconClosedDate, "
	            + "UPDATED_USER = :updatedUser, UPDATED_DATE = :updatedDate "
	            + "WHERE TRN_INV_REC_ID = :trnInvRecId";

	    // Update TRN_INVENTORY_RECONCILE_DETAIL query
	    String updateReconcileDetailQuery = "UPDATE TRN_INVENTORY_RECONCILE_DETAIL "
	            + "SET ITEM_ID = :itemId, ITEM_CODE = :itemCode, BATCH_NO = :batchNo, LOT_NO = :lotNo, "
	            + "EXPIRY_DATE = :expiryDate, QTY_IN_HAND = :qtyInHand, MONTH_ID = :monthId, "
	            + "UPDATED_USER = :updatedUser, UPDATED_DATE = :updatedDate "
	            + "WHERE TRN_INV_REC_ID = :trnInvRecId AND TRN_INV_REC_DETAIL_ID = :trnInvRecDetailId";

	    // Execute updates
	    int rowsUpdatedReconcile = entityManager.createNativeQuery(updateReconcileQuery)
	            .setParameter("accountId", updateRequest.getAccountId())
	            .setParameter("reconStatus", updateRequest.getReconStatus())
	            .setParameter("reconNotes", updateRequest.getReconNotes())
	            .setParameter("recCycleId", updateRequest.getRecCycleId())
	            .setParameter("reconClosedDate", updateRequest.getReconClosedDate())
	            .setParameter("updatedUser", updateRequest.getUpdatedUser())
	            .setParameter("updatedDate", updateRequest.getUpdatedDate())
	            .setParameter("trnInvRecId", trnInvRecId)
	            .executeUpdate();

	    int rowsUpdatedReconcileDetail = entityManager.createNativeQuery(updateReconcileDetailQuery)
	            .setParameter("itemId", updateRequest.getItemId())
	            .setParameter("itemCode", updateRequest.getItemCode())
	            .setParameter("batchNo", updateRequest.getBatchNo())
	            .setParameter("lotNo", updateRequest.getLotNo())
	            .setParameter("expiryDate", updateRequest.getExpiryDate())
	            .setParameter("qtyInHand", updateRequest.getQtyInHand())
	            .setParameter("monthId", updateRequest.getMonthId())
	            .setParameter("updatedUser", updateRequest.getUpdatedUser())
	            .setParameter("updatedDate", updateRequest.getUpdatedDate())
	            .setParameter("trnInvRecId", trnInvRecId)
	            .setParameter("trnInvRecDetailId", updateRequest.getTrnInvRecDetailId())
	            .executeUpdate();

	    return (rowsUpdatedReconcile > 0 || rowsUpdatedReconcileDetail > 0) 
	            ? "Inventory details updated successfully." 
	            : "No records updated.";
	}
	
	
}
