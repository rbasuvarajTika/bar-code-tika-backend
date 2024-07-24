package com.tika.barcode.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;

import com.tika.barcode.dto.request.AddInventoryRequest;
import com.tika.barcode.dto.request.InitiateInventoryRequest;
import com.tika.barcode.dto.request.ModifyInventoryRequest;
import com.tika.barcode.dto.request.SubmitInventoryRequest;
import com.tika.barcode.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String initiateInventoryProc(InitiateInventoryRequest initiateInventoryRequest) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("usp_Inventory_Initiate");
		query.registerStoredProcedureParameter("USER", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ACCOUNT_ID", Integer.class, ParameterMode.IN);
			
		query.setParameter("USER", initiateInventoryRequest.getUser());
		query.setParameter("ACCOUNT_ID", initiateInventoryRequest.getAccountId());
	
		query.execute();

		return "inventory initiated successfully";
	}

	@Override
	public String addInventoryProc(AddInventoryRequest addInventoryRequest) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("usp_Inventory_Add");
		query.registerStoredProcedureParameter("USER", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("TRN_INV_REC_ID", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ITEM_CODE", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("BATCH_NO", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("LOT_NO", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("QTY_IN_HAND", Integer.class, ParameterMode.IN);

			
		query.setParameter("USER", addInventoryRequest.getUser());
		query.setParameter("TRN_INV_REC_ID", addInventoryRequest.getTrnInvRecId());
		query.setParameter("ITEM_CODE", addInventoryRequest.getItemCode());
		query.setParameter("BATCH_NO", addInventoryRequest.getBatchNo());
		query.setParameter("LOT_NO", addInventoryRequest.getLotNo());
		query.setParameter("QTY_IN_HAND", addInventoryRequest.getQtyInHand());
	
		query.execute();

		return "inventory added successfully";
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

}
