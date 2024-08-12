package com.tika.barcode.service.impl;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tika.barcode.dto.request.AddInventoryRequest;
import com.tika.barcode.dto.request.CloseInventoryRequest;
import com.tika.barcode.dto.request.InitiateInventoryRequest;
import com.tika.barcode.dto.request.InventoryReconRequest;
import com.tika.barcode.dto.request.ModifyInventoryRequest;
import com.tika.barcode.dto.request.SubmitInventoryRequest;
import com.tika.barcode.dto.response.AcoountDetailsResponse;
import com.tika.barcode.dto.response.InventoryRecCloseDetailResponse;
import com.tika.barcode.dto.response.InventoryRecDetailResponse;
import com.tika.barcode.dto.response.InventoryReconResonse;
import com.tika.barcode.service.InventoryService;
import com.tika.barcode.utility.PdfGenerator;

@Service
public class InventoryServiceImpl implements InventoryService {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private PdfGenerator pdfGenerator;

	@Override
	public Integer initiateInventoryProc(InitiateInventoryRequest initiateInventoryRequest) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("usp_Inventory_Initiate");
		query.registerStoredProcedureParameter("USER", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("ACCOUNT_ID", Integer.class, ParameterMode.IN);


		query.setParameter("USER", initiateInventoryRequest.getUser());
		query.setParameter("ACCOUNT_ID", initiateInventoryRequest.getAccountId());

		query.execute();
		// Retrieve the output parameter
		Integer newPrimaryKey = retrievePrimaryKey(initiateInventoryRequest);

		return newPrimaryKey;
	}

	@Override
	public Integer addInventoryProc(AddInventoryRequest addInventoryRequest) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("usp_Inventory_Add");
		query.registerStoredProcedureParameter("USER", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("TRN_INV_REC_ID", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("ITEM_CODE", String.class, ParameterMode.IN);
		//query.registerStoredProcedureParameter("ITEM_NAME", String.class, ParameterMode.IN);
		//query.registerStoredProcedureParameter("BATCH_NO", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("LOT_NO", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("QTY_IN_HAND", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("EXPIRY_DATE", String.class, ParameterMode.IN);

		query.setParameter("USER", addInventoryRequest.getUser());
		query.setParameter("TRN_INV_REC_ID", addInventoryRequest.getTrnInvRecId());
		query.setParameter("ITEM_CODE", addInventoryRequest.getItemCode());
		//query.setParameter("ITEM_NAME", addInventoryRequest.getItemName());
		//query.setParameter("BATCH_NO", addInventoryRequest.getBatchNo());
		query.setParameter("LOT_NO", addInventoryRequest.getLotNo());
		query.setParameter("QTY_IN_HAND", addInventoryRequest.getQtyInHand());
		query.setParameter("EXPIRY_DATE", addInventoryRequest.getExpiryDate());

		query.execute();
		// Retrieve the output parameter
		Integer newPrimaryKey = retrievePrimaryKey(addInventoryRequest);

		return newPrimaryKey;
	}

	@Override
	public String modifyInventoryProc(ModifyInventoryRequest modifyInventoryRequest) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("usp_Inventory_Mod");
		query.registerStoredProcedureParameter("USER", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("ITEM_CODE", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("TRN_INV_REC_DETAIL_ID", Integer.class, ParameterMode.IN);

		query.registerStoredProcedureParameter("QTY_IN_HAND", Integer.class, ParameterMode.IN);

		query.setParameter("USER", modifyInventoryRequest.getUser());
		query.setParameter("TRN_INV_REC_DETAIL_ID", modifyInventoryRequest.getTrnInvRecDetailId());
		query.setParameter("ITEM_CODE", modifyInventoryRequest.getItemCode());
		query.setParameter("QTY_IN_HAND", modifyInventoryRequest.getQtyInHand());

		query.execute();

		return "inventory modified successfully";
	}

	@Override
	public String submitInventoryProc(SubmitInventoryRequest submitInventoryRequest) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("usp_Inventory_Submit");
		query.registerStoredProcedureParameter("USER", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("TRN_INV_REC_ID", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("RECON_NOTES", String.class, ParameterMode.IN);

		query.setParameter("USER", submitInventoryRequest.getUser());
		query.setParameter("TRN_INV_REC_ID", submitInventoryRequest.getTrnInvRecId());
		query.setParameter("RECON_NOTES", submitInventoryRequest.getReconNotes());

		query.execute();

		return "inventory submitted successfully";
	}

	@Override
	public String insertInventoryRecon(List<InventoryReconRequest> inventoryReconRequest) {
		InventoryReconRequest  getInvRec = inventoryReconRequest.get(0);
		String checkQuery = " select a.TRN_INV_REC_ID \r\n"
				+ "from TRN_INVENTORY_RECONCILE a \r\n"
				+ "where a.ACCOUNT_ID=?1 and a.CREATED_USER=?2 and a.RECON_STATUS=?3 ";
		List<Object> queryResult = entityManager.createNativeQuery(checkQuery)
				.setParameter(1, getInvRec.getAccountId())
				.setParameter(2, getInvRec.getUser())
				.setParameter(3, "Submit")
				.getResultList();
		String response ="";
		Integer trnInvRecId = getInvRec.getAccountId();
		//First Condition Null 
         if(queryResult.isEmpty()) {
        	 InitiateInventoryRequest inventoryRequest = new InitiateInventoryRequest(getInvRec.getUser(),
        			 getInvRec.getAccountId());
        	 trnInvRecId = initiateInventoryProc(inventoryRequest); 
         }
         
    		List<Object[]> queryResultDetails = entityManager
    				.createNativeQuery("select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,b.ADDRESS2,"
    						+ "b.CITY,b.STATE,b.ZIP,\r\n"
    						+ "d.ITEM_ID,d.ITEM_NUMBER MATERIAL_KEY,d.ITEM_DESC1,a.Batch,a.LOT_NO,a.Expiry_Date,"
    						+ "a.Total_Stock QTY_IN_HAND\r\n" + "from FCT_CONSIGNMENT_INVENTORY a \r\n"
    						+ "join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
    						+ "join DIM_TERRITORY c on (a.TERRITORY_ID=c.TERRITORY_ID)\r\n"
    						+ "join DIM_ITEM d on (a.ITEM_ID=d.ITEM_ID)\r\n" )
    				.getResultList();

    		List<AcoountDetailsResponse> acoountDetailsResponses = queryResultDetails.stream()
    				.map(this::mapToObjectArrayAccDetResponse).collect(Collectors.toList());
         
		for(InventoryReconRequest request : inventoryReconRequest) {
			
			@SuppressWarnings("unlikely-arg-type")
			List<AcoountDetailsResponse> acoountDetailsResponsesNew = 
			acoountDetailsResponses.stream().filter(e->e.getAccountId().equals(request.getAccountId()) 
					 // && e.getBatch().equalsIgnoreCase(request.getBatchNo())
					  && e.getLotNo().equalsIgnoreCase(request.getLotNo())
					  && (e.getItemNumber().equalsIgnoreCase(request.getItemNumber()) || e.getItemId().equals(request.getItemNumber()))
					  && e.getQtyInHand().intValue()==request.getQtyInHand()).collect(Collectors.toList());
			if(acoountDetailsResponsesNew.size() == 0) {
				response = insertInventoryRecon(request,trnInvRecId);
			}

		}
		return response;
		
	}

	
	private String insertInventoryRecon(InventoryReconRequest inventoryReconRequest,Integer invRecId) {
		String submitResponse = ""; 

		List<Object> queryResult = entityManager
				.createNativeQuery("select a.TRN_INV_REC_DETAIL_ID "
						+ "from TRN_INVENTORY_RECONCILE_DETAIL a \r\n"
				+ "where a.ITEM_CODE=?1 AND a.LOT_NO=?2")
				.setParameter(1, inventoryReconRequest.getItemNumber())
				.setParameter(2, inventoryReconRequest.getLotNo())
				.getResultList();

		if (queryResult.isEmpty()) {
			AddInventoryRequest addInventoryRequest = new AddInventoryRequest(inventoryReconRequest.getUser(), 
					invRecId,
					inventoryReconRequest.getItemNumber(),
					inventoryReconRequest.getItemName(),
					//inventoryReconRequest.getBatchNo(),
					inventoryReconRequest.getLotNo(), 
					inventoryReconRequest.getQtyInHand(),
					inventoryReconRequest.getExpiryDate());
			Integer invRecDetailId = addInventoryProc(addInventoryRequest);
		}else {
			Integer trnInvRecDetId = (Integer) queryResult.get(0);
			ModifyInventoryRequest mapMod = new ModifyInventoryRequest();
			mapMod.setTrnInvRecDetailId(trnInvRecDetId);
			mapMod.setUser(inventoryReconRequest.getUser());
			mapMod.setItemCode(inventoryReconRequest.getItemNumber());
			mapMod.setQtyInHand(inventoryReconRequest.getQtyInHand());
			String modifyResponse = modifyInventoryProc(mapMod);
		}
			SubmitInventoryRequest submitInventoryRequest = new SubmitInventoryRequest(inventoryReconRequest.getUser(),
					invRecId, inventoryReconRequest.getReconNotes());
			submitResponse = submitInventoryProc(submitInventoryRequest);
	
		return submitResponse;
	}

	private Integer retrievePrimaryKey(InitiateInventoryRequest initiateInventoryRequest) {
        // Assuming there is a CreatedAt timestamp or an auto-incrementing ID field
        String sql = "select TOP 1 a.TRN_INV_REC_ID from TRN_INVENTORY_RECONCILE a "
        		+ "	where a.ACCOUNT_ID=?1 AND a.CREATED_USER=?2  ORDER BY a.CREATED_DATE DESC ";

        // If no CreatedAt timestamp, use the auto-incrementing primary key
        // String sql = "SELECT id FROM Inventory WHERE User = :user AND AccountId = :accountId ORDER BY id DESC LIMIT 1";

        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.setParameter(1, initiateInventoryRequest.getAccountId());
        nativeQuery.setParameter(2, initiateInventoryRequest.getUser());

        // Execute the query and retrieve the primary key
        Integer primaryKey = (Integer) nativeQuery.getSingleResult();

        return primaryKey;
    }
	
	private Integer retrievePrimaryKey(AddInventoryRequest addInventoryRequest) {
        // Assuming there is a CreatedAt timestamp or an auto-incrementing ID field
        String sql = "select TOP 1 b.TRN_INV_REC_DETAIL_ID from TRN_INVENTORY_RECONCILE_DETAIL b "
        		+ "	where b.CREATED_USER=?1 AND b.TRN_INV_REC_ID=?2  ORDER BY b.CREATED_DATE DESC ";

        // If no CreatedAt timestamp, use the auto-incrementing primary key
        // String sql = "SELECT id FROM Inventory WHERE User = :user AND AccountId = :accountId ORDER BY id DESC LIMIT 1";

        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.setParameter(1, addInventoryRequest.getUser());
        nativeQuery.setParameter(2, addInventoryRequest.getTrnInvRecId());

        // Execute the query and retrieve the primary key
        Integer primaryKey = (Integer) nativeQuery.getSingleResult();

        return primaryKey;
    }

	@Override
	public List<InventoryReconResonse> getInvRecByAccIdAndUser(String user) {
		String sql = "select a.TRN_INV_REC_ID,a.ACCOUNT_ID,a.RECON_STATUS,a.RECON_NOTES,a.CREATED_USER,"
				+ " a.CREATED_DATE,a.UPDATED_USER,a.UPDATED_DATE from TRN_INVENTORY_RECONCILE a "
        		+ "	where a.CREATED_USER=?1 ";
		Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.setParameter(1, user);
        List<Object[]> queryResult = nativeQuery.getResultList();
        List<InventoryReconResonse> inventoryReconResonses = queryResult.stream()
				.map(this::mapToObjectArrayInvRecResponse).collect(Collectors.toList());
		return inventoryReconResonses;
	}

	private InventoryReconResonse mapToObjectArrayInvRecResponse(Object[] record) {
		InventoryReconResonse response = new InventoryReconResonse();
		response.setTrnInvRecId((Integer) record[0]);
		response.setAccountId((Integer) record[1]);
		response.setReconStatus((String) record[2]);
		response.setReconNotes((String) record[3]);
		response.setCreatedUser((String) record[4]);
		Timestamp createdDate = (Timestamp) record[5];
		if(createdDate!=null)
		  response.setCreatedDate(createdDate.toLocalDateTime());
		response.setUpdatedUser((String) record[6]);
		Timestamp updated = (Timestamp) record[7];
		if(updated!=null)
		  response.setUpdatedDate(updated.toLocalDateTime());

		return response;
	}

	@Override
	public List<InventoryRecDetailResponse> getInvRecDetails() {
		String sql = " select distinct b.ACCOUNT_ID,c.ACCOUNT_NAME,a.TRN_INV_REC_ID,"
				+ "b.REC_CYCLE_ID,b.CREATED_DATE,b.RECON_CLOSED_DATE,b.RECON_STATUS\r\n"
				+ "from TRN_INVENTORY_RECONCILE_DETAIL a \r\n"
				+ "join TRN_INVENTORY_RECONCILE b on (a.TRN_INV_REC_ID=b.TRN_INV_REC_ID)\r\n"
				+ "join DIM_ACCOUNT c on (b.ACCOUNT_ID=c.ACCOUNT_ID)\r\n"
				+ "where b.RECON_STATUS IN ('Submit','Closed')";
		Query nativeQuery = entityManager.createNativeQuery(sql);
        List<Object[]> queryResult = nativeQuery.getResultList();
        List<InventoryRecDetailResponse> inventoryReconDetailResonses = queryResult.stream()
				.map(this::mapToObjectArrayInvRecDetResponse).collect(Collectors.toList());
		return inventoryReconDetailResonses;
	}
	
	@Override
	public List<InventoryRecCloseDetailResponse> getInvRecCloseDetails() {
		String sql = " select  b.ACCOUNT_ID,c.ACCOUNT_NAME,a.TRN_INV_REC_ID,a.TRN_INV_REC_DETAIL_ID,a.BATCH_NO,"
				+ "a.ITEM_ID,a.ITEM_CODE,a.LOT_NO,b.REC_CYCLE_ID,b.CREATED_DATE,A.QTY_IN_HAND,B.RECON_CLOSED_DATE,b.RECON_STATUS\r\n"
				+ "from TRN_INVENTORY_RECONCILE_DETAIL a \r\n"
				+ "join TRN_INVENTORY_RECONCILE b on (a.TRN_INV_REC_ID=b.TRN_INV_REC_ID)\r\n"
				+ "join DIM_ACCOUNT c on (b.ACCOUNT_ID=c.ACCOUNT_ID)\r\n"
				+ "where b.RECON_STATUS IN ('Closed')";
		Query nativeQuery = entityManager.createNativeQuery(sql);
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
		if(startDate!=null)
			 response.setReconStartDate(startDate.toLocalDateTime());
		Date closedDate = (Date) record[5];
		if(closedDate!=null)
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
		//response.setBatchNo((String) record[4]);
		response.setItemId((Integer) record[5]);
		response.setItemNumber((String) record[6]);
		response.setLotNo((String) record[7]);
		response.setReconCycleId((Integer) record[8]);
		Timestamp startDate = (Timestamp) record[9];
		if(startDate!=null)
			 response.setReconStartDate(startDate.toLocalDateTime());
		
		response.setQtyInHand((BigDecimal) record[10]);
		Date closedDate = (Date) record[11];
		if(closedDate!=null)
		 response.setReconClosedDate(closedDate.toLocalDate()); 
		response.setReconStatus((String) record[12]);
		return response;
	}

	@Override
	public String closeInventoryRecon(CloseInventoryRequest closeInventoryRequest) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("usp_Inventory_Close");
		query.registerStoredProcedureParameter("USER", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("TRN_INV_REC_ID", Integer.class, ParameterMode.IN);

		query.setParameter("USER", closeInventoryRequest.getUser());
		query.setParameter("TRN_INV_REC_ID", closeInventoryRequest.getTrnInvRecId());

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
		//response.setBatch((String) record[10]);
		response.setLotNo((String) record[11]);
		Date expirydate = (Date) record[12];
		if(expirydate!=null)
		  response.setExpiryDate(expirydate.toLocalDate());
		response.setQtyInHand((BigDecimal) record[13]);

		return response;
	}

	@Override	
	public List<InventoryRecCloseDetailResponse> getInvRecCloseDetByTrnInvRecId(Integer trnInvRecId) {
		String sql = " select  b.ACCOUNT_ID,c.ACCOUNT_NAME,a.TRN_INV_REC_ID,a.TRN_INV_REC_DETAIL_ID,a.BATCH_NO,"
				+ "a.ITEM_ID,a.ITEM_CODE,a.LOT_NO,b.REC_CYCLE_ID,b.CREATED_DATE,A.QTY_IN_HAND,B.RECON_CLOSED_DATE,b.RECON_STATUS\r\n"
				+ "from TRN_INVENTORY_RECONCILE_DETAIL a \r\n"
				+ "join TRN_INVENTORY_RECONCILE b on (a.TRN_INV_REC_ID=b.TRN_INV_REC_ID)\r\n"
				+ "join DIM_ACCOUNT c on (b.ACCOUNT_ID=c.ACCOUNT_ID)\r\n"
				+ "where b.RECON_STATUS IN ('Closed') AND a.TRN_INV_REC_ID =?1";
		Query nativeQuery = entityManager.createNativeQuery(sql).setParameter(1, trnInvRecId);
        List<Object[]> queryResult = nativeQuery.getResultList();
        List<InventoryRecCloseDetailResponse> inventoryReconDetailResonses = queryResult.stream()
				.map(this::mapToObjectArrayInvRecDetCloseResponse).collect(Collectors.toList());
		return inventoryReconDetailResonses;
	}
	
	


//    public void createInventoryPdf(Integer trnInvRecId) throws Exception {
//    	List<InventoryRecCloseDetailResponse> details = getInvRecCloseDetByTrnInvRecId(trnInvRecId);
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("details", details);
//
//        String fileName = "InventoryReport_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
//        pdfGenerator.generatePdf("inventory-report", data, fileName);
//    }
    public byte[] createInventoryPdf(Integer trnInvRecId) throws Exception {
    	List<InventoryRecCloseDetailResponse> details = getInvRecCloseDetByTrnInvRecId(trnInvRecId);
        HashMap<String, Object> data = new HashMap<>();
        data.put("details", details);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        pdfGenerator.generatePdf("inventory-report", data, os);
        return os.toByteArray();
    }
	
//	@Override
//	public byte[] generatePdf(Integer trnInvRecId) throws IOException {
//		List<InventoryRecCloseDetailResponse> details = getInvRecCloseDetByTrnInvRecId(trnInvRecId);
//        try (PDDocument document = new PDDocument()) {
//            PDPage page = new PDPage();
//            document.addPage(page);
//
//            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
//                
//                // Title
//                contentStream.beginText();
//                contentStream.newLineAtOffset(50, 750);
//                contentStream.showText("Inventory Reconciliation Close Details");
//                contentStream.endText();
//
//                // Draw vertical headers
//                float startX = 50;
//                float startY = 700;
//                float headerHeight = 500;  // Adjust based on your need
//                String[] headers = {
//                    "Account ID", "Account Name", "Trn Inv Rec ID", "Trn Inv Rec Details ID", 
//                    "Batch No", "Item ID", "Material Key", "Recon Cycle ID", 
//                    "Lot No", "Recon Start Date", "Qty In Hand", "Recon Closed Date", "Recon Status"
//                };
//
//                for (int i = 0; i < headers.length; i++) {
//                    float x = startX + (i * 60);  // Adjust spacing based on header width
//                    drawVerticalText(contentStream, headers[i], x, startY, headerHeight);
//                }
//
//                // Draw data rows
//                contentStream.beginText();
//                contentStream.newLineAtOffset(50, 200);
//                for (InventoryRecCloseDetailResponse detail : details) {
//                    contentStream.showText(String.format("%d | %s | %d | %d | %s | %d | %s | %d | %s | %s | %s | %s",
//                            detail.getAccountId(), detail.getAccountName(), detail.getTrnInvRecId(), detail.getTrnInvRecDetailsId(),
//                            detail.getBatchNo(), detail.getItemId(), detail.getMaterialKey(), detail.getReconCycleId(),
//                            detail.getLotNo(), detail.getReconStartDate(), detail.getQtyInHand(), detail.getReconClosedDate(),
//                            detail.getReconStatus()));
//                    contentStream.newLine();
//                }
//                contentStream.endText();
//            }
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            document.save(baos);
//            return baos.toByteArray();
//        }
//    }
//
//    private void drawVerticalText(PDPageContentStream contentStream, String text, float x, float y, float height) throws IOException {
//        // Set up text rotation
//        contentStream.setTextMatrix(AffineTransform.getRotateInstance(-Math.PI / 2, x, y));
//        contentStream.beginText();
//        contentStream.newLineAtOffset(x, y);
//        contentStream.showText(text);
//        contentStream.endText();
//    }


}
