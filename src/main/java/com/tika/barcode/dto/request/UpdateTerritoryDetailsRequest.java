package com.tika.barcode.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTerritoryDetailsRequest {
	
	private Integer territoryId;
	private String territoryCd ;
	private String territoryName;
	private Integer regionId;
	private String	regionCd;
	private String regionName;
	private Integer areaId;
	private String areaCd;
	private String areaName;
	private Integer companyId;
	private String companyCd;
	private String companyName;
	private String activeInd;
	private Integer userId;
	private String createUser;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDt;
	private String updateUser;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDt;
	private String country;

}
