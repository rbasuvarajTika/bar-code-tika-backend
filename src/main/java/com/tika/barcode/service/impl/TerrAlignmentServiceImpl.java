package com.tika.barcode.service.impl;

import java.math.BigDecimal;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tika.barcode.constants.ParameterConstant;
import com.tika.barcode.constants.ProcedureConstant;
import com.tika.barcode.constants.QueryConstant;
import com.tika.barcode.dto.request.TerrAlignmentDetailsRequest;
import com.tika.barcode.dto.response.TerrAlignmentResponse;
import com.tika.barcode.service.TerrAlignmentService;
import com.tika.barcode.service.TerritoryService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;



@Service
public class TerrAlignmentServiceImpl implements TerrAlignmentService {
	
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<TerrAlignmentResponse> getAllTerrAllignmentDetails() {
		   Query nativeQuery = entityManager.createNativeQuery(QueryConstant.GET_ALL_XREF_TERR_ALIGNMNT);
		    List<Object[]> queryResult = nativeQuery.getResultList();
		    List<TerrAlignmentResponse> terrAlignmentResponse = queryResult.stream()
		            .map(this::mapToObjectArrayTerrAlignResponse).collect(Collectors.toList());
		    return terrAlignmentResponse;
		}
	

		
		private TerrAlignmentResponse mapToObjectArrayTerrAlignResponse(Object[] record) {
			
			TerrAlignmentResponse response = new TerrAlignmentResponse();
			response.setTerritoryId((Integer) record[0]);
			response.setAccountId((Integer) record[1]);
			Timestamp rfrshDate = (Timestamp) record[2];
			if(rfrshDate!=null)
			  response.setRfrshDate(rfrshDate.toLocalDateTime());
			response.setCreateUser((String) record[3]);
			response.setOldTerrId((Integer) record[4]);
			Timestamp updateDate = (Timestamp) record[5];
			if(updateDate!=null)
			  response.setUpdateDate(updateDate.toLocalDateTime());
			response.setUpdateUser((String) record[6]);
			
			return response;
		}

		
		@Override
	    @Transactional
	    public void updateTerrAlignmentDetails(Integer territoryId, TerrAlignmentResponse terrAlignmentResponse) {
	        // Initialize the StringBuilder to build the dynamic SQL query
	        StringBuilder updateQuery = new StringBuilder("UPDATE XREF_TERR_ALIGNMNT SET ");

	        // StringJoiner will help us avoid trailing commas
	        StringJoiner setClauses = new StringJoiner(", ");

	        // Conditionally add the fields if they're not null
	        if (terrAlignmentResponse.getAccountId() != null) {
	            setClauses.add("ACCOUNT_ID = :accountId");
	        }
	        if (terrAlignmentResponse.getRfrshDate() != null) {
	            setClauses.add("RFRSH_DATE = :rfrshDate");
	        }
	        if (terrAlignmentResponse.getCreateUser() != null) {
	            setClauses.add("CREATE_USER = :createUser");
	        }
	        if (terrAlignmentResponse.getOldTerrId() != null) {
	            setClauses.add("OLD_TERR_ID = :oldTerrId");
	        }
	        
	        // Update date is always set with GETDATE(), no need to validate null
            setClauses.add("UPDATE_DATE = GETDATE()");
	        if (terrAlignmentResponse.getUpdateUser() != null) {
	            setClauses.add("UPDATE_USER = :updateUser");
	        }

	        // Add the conditions to the SET clause
	        updateQuery.append(setClauses.toString());
	        
	        // Add the WHERE condition
	        updateQuery.append(" WHERE TERRITORY_ID = :territoryId");

	        // Create the query
	        Query query = entityManager.createNativeQuery(updateQuery.toString());

	        // Set parameters
	        if (terrAlignmentResponse.getAccountId() != null) {
	            query.setParameter("accountId", terrAlignmentResponse.getAccountId());
	        }
	        if (terrAlignmentResponse.getRfrshDate() != null) {
	            query.setParameter("rfrshDate", terrAlignmentResponse.getRfrshDate());
	        }
	        if (terrAlignmentResponse.getCreateUser() != null) {
	            query.setParameter("createUser", terrAlignmentResponse.getCreateUser());
	        }
	        if (terrAlignmentResponse.getOldTerrId() != null) {
	            query.setParameter("oldTerrId", terrAlignmentResponse.getOldTerrId());
	        }
	        if (terrAlignmentResponse.getUpdateUser() != null) {
	            query.setParameter("updateUser", terrAlignmentResponse.getUpdateUser());
	        }
	        query.setParameter("territoryId", territoryId);

	        // Execute the update query
	        query.executeUpdate();
	    }
	}
		
	
