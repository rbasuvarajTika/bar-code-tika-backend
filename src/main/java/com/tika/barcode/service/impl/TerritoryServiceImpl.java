package com.tika.barcode.service.impl;

import java.sql.Date;



import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.tika.barcode.constants.ParameterConstant;
import com.tika.barcode.constants.ProcedureConstant;
import com.tika.barcode.constants.QueryConstant;

import com.tika.barcode.dto.request.UpdateTerritoryDetailsRequest;
import com.tika.barcode.dto.response.TerrAlignmentResponse;
import com.tika.barcode.dto.response.TerritoryResponse;
import com.tika.barcode.dto.response.UserDetailsResponse;
import com.tika.barcode.entity.Territory;
import com.tika.barcode.entity.User;
import com.tika.barcode.enums.ErrorCodes;
import com.tika.barcode.exceptions.NSException;
import com.tika.barcode.repo.TerritoryRepository;
import com.tika.barcode.repo.UserRepository;
import com.tika.barcode.service.TerritoryService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;


@Service
public class TerritoryServiceImpl implements TerritoryService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private TerritoryRepository territoryRepository;
	
	@Override
	public List<TerritoryResponse> getAllTerritoryDetails() {
		   Query nativeQuery = entityManager.createNativeQuery(QueryConstant.GET_ALL_TERRITORY);
		    List<Object[]> queryResult = nativeQuery.getResultList();
		    List<TerritoryResponse> territoryResponse = queryResult.stream()
		            .map(this::mapToObjectArrayTerritoryResponse).collect(Collectors.toList());
		    return territoryResponse;
		}
	

	
	private TerritoryResponse mapToObjectArrayTerritoryResponse(Object[] record) {
		
		TerritoryResponse response = new TerritoryResponse();

		response.setTerritoryId((Integer)record[0]);
		response.setTerritoryCd((String)record[1]);
		response.setTerritoryName((String)record[2]) ;
		response.setRegionId((Integer)record[3]) ;
		response.setRegionCd((String)record[4]) ;
		response.setRegionName((String)record[5]) ;
		response.setAreaId((Integer)record[6]) ;
		response.setAreaCd((String)record[7]) ;
		response.setAreaName((String)record[8]) ;
		response.setCompanyId((Integer)record[9]) ;
		response.setCompanyCd((String)record[10]) ;
		response.setCompanyName((String)record[11]) ;
		response.setActiveInd((String)record[12]) ;
		response.setUserId((Integer)record[13]) ;
		response.setCreateUser((String)record[14]) ;
		Timestamp createDt = (Timestamp) record[15];
		if(createDt!=null)
		  response.setCreateDt(createDt.toLocalDateTime());
		response.setUpdateUser((String)record[16]) ;
		Timestamp updateDt = (Timestamp) record[17];
		if(updateDt!=null)
		  response.setUpdateDt(updateDt.toLocalDateTime());
		response.setCountry((String)record[18]) ;
		return response;
	}


//	@Override
//	public String updateTerritoryDetails(Integer territoryId, UpdateTerritoryDetailsRequest updateTerritoryDetails) {
//	try {
//	StoredProcedureQuery query = entityManager.createStoredProcedureQuery(ProcedureConstant.USP_TERRITORY_DETAILS_UPDATE);
//	
//	query.registerStoredProcedureParameter(ParameterConstant.TERRITORY_ID, Integer.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.TERRITORY_CD, String.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.TERRITORY_NAME, String.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.REGION_ID, Integer.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.REGION_CD, String.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.REGION_NAME, String.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.AREA_ID, Integer.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.AREA_CD, String.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.AREA_NAME, String.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.COMPANY_ID, Integer.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.COMPANY_CD, String.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.COMPANY_NAME, String.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.ACTIVE_IND, String.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.USER_ID, Integer.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.CREATE_USER, String.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.CREATE_DT, LocalDateTime.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.UPDATE_USER, String.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.UPDATE_DT, LocalDateTime.class, ParameterMode.IN);
//	query.registerStoredProcedureParameter(ParameterConstant.COUNTRY, String.class, ParameterMode.IN);
//	
//	query.setParameter(ParameterConstant.TERRITORY_ID, territoryId);
//	query.setParameter(ParameterConstant.TERRITORY_CD, updateTerritoryDetails.getTerritoryCd());
//	query.setParameter(ParameterConstant.TERRITORY_NAME, updateTerritoryDetails.getTerritoryName());
//	query.setParameter(ParameterConstant.REGION_ID, updateTerritoryDetails.getRegionId());
//	query.setParameter(ParameterConstant.REGION_CD, updateTerritoryDetails.getRegionCd());
//	query.setParameter(ParameterConstant.REGION_NAME, updateTerritoryDetails.getRegionName());
//	query.setParameter(ParameterConstant.AREA_ID, updateTerritoryDetails.getAreaId());
//	query.setParameter(ParameterConstant.AREA_CD, updateTerritoryDetails.getAreaCd());
//	query.setParameter(ParameterConstant.AREA_NAME, updateTerritoryDetails.getAreaName());
//	query.setParameter(ParameterConstant.COMPANY_ID, updateTerritoryDetails.getCompanyId());
//	query.setParameter(ParameterConstant.COMPANY_CD, updateTerritoryDetails.getCompanyCd());
//	query.setParameter(ParameterConstant.COMPANY_NAME, updateTerritoryDetails.getCompanyName());
//	query.setParameter(ParameterConstant.ACTIVE_IND, updateTerritoryDetails.getActiveInd());
//	query.setParameter(ParameterConstant.USER_ID, updateTerritoryDetails.getUserId());
//	query.setParameter(ParameterConstant.CREATE_USER, updateTerritoryDetails.getCreateUser());
//	query.setParameter(ParameterConstant.CREATE_DT, updateTerritoryDetails.getCreateDt());
//	query.setParameter(ParameterConstant.UPDATE_USER, updateTerritoryDetails.getUpdateUser());
//	query.setParameter(ParameterConstant.UPDATE_DT, updateTerritoryDetails.getUpdateDt());
//	query.setParameter(ParameterConstant.COUNTRY, updateTerritoryDetails.getCountry());
//
//		query.execute();
//		return "Territory Details updated successfully";
//	}catch(Exception e) {
//		throw new RuntimeException("Territory details update failed",e);
//	}
//	
//	}
	
//	@Override
//	public String updateTerritoryDetails(Integer territoryId, TerritoryResponse territoryDetails) {
//		Optional<Territory> existingTerritory = territoryRepository.findById(territoryId);
//	    if (existingTerritory.isPresent()) {
//	    	Territory territory = existingTerritory.get();
//	        
//	       
//	    	territory.setTerritoryCd(territoryDetails.getTerritoryCd());
//	    	territory.setTerritoryName(territoryDetails.getTerritoryName());
//	    	territory.setRegionId(territoryDetails.getRegionId());
//	    	territory.setRegionCd(territoryDetails.getRegionCd());
//	    	territory.setRegionName(territoryDetails.getRegionName());
//	    	territory.setAreaId(territoryDetails.getAreaId());
//	    	territory.setAreaCd(territoryDetails.getAreaCd());
//	    	territory.setAreaName(territoryDetails.getAreaName());
//	    	territory.setCompanyId(territoryDetails.getCompanyId());
//	    	territory.setCompanyCd(territoryDetails.getCompanyCd());
//	    	territory.setCompanyName(territoryDetails.getCompanyName());
//	    	territory.setActiveInd(territoryDetails.getActiveInd());
//	    	territory.setUserId(territoryDetails.getUserId());
//	    	territory.setCreateUser(territoryDetails.getCreateUser());
//	    	territory.setCreateDt(territoryDetails.getCreateDt());
//	    	territory.setUpdateUser(territoryDetails.getUpdateUser());
//	    	territory.setUpdateDt(territoryDetails.getUpdateDt());
//	    	territory.setCountry(territoryDetails.getCountry());
//	       
//	        
//	        // Continue with other fields...
//	        
//	    	territoryRepository.save(territory); // Save updated user to the database
//	        return "User updated successfully";
//	    } else {
//	        throw new NSException(ErrorCodes.NOT_FOUND, "User with ID " + territoryId + " not found");
//	    }		
//}

	
	
	@Override
    @Transactional
    public void updateTerritoryDetails(Integer territoryId, UpdateTerritoryDetailsRequest updateTerritory) {
        // Initialize the StringBuilder to build the dynamic SQL query
        StringBuilder updateQuery = new StringBuilder("UPDATE DIM_TERRITORY SET ");

        // StringJoiner will help us avoid trailing commas
        StringJoiner setClauses = new StringJoiner(", ");

        // Conditionally add the fields if they're not null
        
        if (updateTerritory.getTerritoryCd() != null) {
            setClauses.add("TERRITORY_CD = :territoryCd");
        }
        if (updateTerritory.getTerritoryName() != null) {
            setClauses.add("TERRITORY_NAME = :territoryName");
        }
        if (updateTerritory.getRegionId() != null) {
            setClauses.add("REGION_ID = :regionId");
        }
        if (updateTerritory.getRegionCd() != null) {
            setClauses.add("REGION_CD = :regionCd");
        }
        if (updateTerritory.getRegionName() != null) {
            setClauses.add("REGION_NAME = :regionName");
        }
        if (updateTerritory.getAreaId() != null) {
            setClauses.add("AREA_ID = :areaId");
        }
        if (updateTerritory.getAreaCd() != null) {
            setClauses.add("AREA_CD = :areaCd");
        }
        if (updateTerritory.getAreaName() != null) {
            setClauses.add("AREA_NAME = :areaName");
        }
        if (updateTerritory.getCompanyId() != null) {
            setClauses.add("COMPANY_ID = :companyId");
        }
        if (updateTerritory.getCompanyCd() != null) {
            setClauses.add("COMPANY_CD = :companyCd");
        }
        if (updateTerritory.getCompanyName() != null) {
            setClauses.add("COMPANY_NAME = :companyName");
        }
        if (updateTerritory.getActiveInd() != null) {
            setClauses.add("ACTIVE_IND = :activeInd");
        }
        if (updateTerritory.getUserId() != null) {
            setClauses.add("USER_ID = :userId");
        }
        if (updateTerritory.getCreateUser() != null) {
            setClauses.add("CREATE_USER = :createUser");
        }
        if (updateTerritory.getCreateDt() != null) {
            setClauses.add("CREATE_DT = :createDt");
        }
        if (updateTerritory.getUpdateUser() != null) {
            setClauses.add("UPDATE_USER = :updateUser");
        }
        
     // Update date is always set with GETDATE(), no need to validate null
        
        setClauses.add("UPDATE_DT = GETDATE()");
        
        if (updateTerritory.getTerritoryId() != null) {
            setClauses.add("COUNTRY = :territoryId");
        }

        // Add the conditions to the SET clause
        updateQuery.append(setClauses.toString());
        
        // Add the WHERE condition
        updateQuery.append(" WHERE TERRITORY_ID = :territoryId");

        // Create the query
        Query query = entityManager.createNativeQuery(updateQuery.toString());

        // Set parameters
        if (updateTerritory.getTerritoryCd() != null) {
            query.setParameter("territoryCd", updateTerritory.getTerritoryCd());
        }
        if (updateTerritory.getTerritoryName() != null) {
            query.setParameter("territoryName", updateTerritory.getTerritoryName());
        }
        if (updateTerritory.getRegionId() != null) {
            query.setParameter("regionId", updateTerritory.getRegionId());
        }
        if (updateTerritory.getRegionCd() != null) {
            query.setParameter("regionCd", updateTerritory.getRegionCd());
        }
        if (updateTerritory.getRegionName() != null) {
            query.setParameter("regionName", updateTerritory.getRegionName());
        }
        if (updateTerritory.getAreaId() != null) {
            query.setParameter("areaId", updateTerritory.getAreaId());
        }
        if (updateTerritory.getAreaCd() != null) {
            query.setParameter("areaCd", updateTerritory.getAreaCd());
        }
        if (updateTerritory.getAreaName() != null) {
            query.setParameter("areaName", updateTerritory.getAreaName());
        }
        if (updateTerritory.getCompanyId() != null) {
            query.setParameter("companyId", updateTerritory.getCompanyId());
        }
        if (updateTerritory.getCompanyCd() != null) {
            query.setParameter("companyCd", updateTerritory.getCompanyCd());
        }
        if (updateTerritory.getCompanyName() != null) {
            query.setParameter("companyName", updateTerritory.getCompanyName());
        }
        if (updateTerritory.getActiveInd() != null) {
            query.setParameter("activeInd", updateTerritory.getActiveInd());
        }
        if (updateTerritory.getUserId() != null) {
            query.setParameter("userId", updateTerritory.getUserId());
        }
        if (updateTerritory.getCreateUser() != null) {
            query.setParameter("createUser", updateTerritory.getCreateUser());
        }
        if (updateTerritory.getCreateDt() != null) {
            query.setParameter("createDt", updateTerritory.getCreateDt());
        }
        if (updateTerritory.getUpdateUser() != null) {
            query.setParameter("updateUser", updateTerritory.getUpdateUser());
        }
        if (updateTerritory.getCountry() != null) {
            query.setParameter("country", updateTerritory.getCountry());
        }
       
        query.setParameter("territoryId", territoryId);

        // Execute the update query
        query.executeUpdate();
    }
			
}