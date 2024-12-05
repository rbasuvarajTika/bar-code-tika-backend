package com.tika.barcode.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Processes an {@link User } .request
 * 
 * @author Raghu
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DIM_TERRITORY", schema = "dbo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Territory {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 	@Column(name = "TERRITORY_ID")
	    private Integer territoryId;

	    @Column(name = "TERRITORY_CD")
	    private String territoryCd;

	    @Column(name = "TERRITORY_NAME")
	    private String territoryName;

	    @Column(name = "REGION_ID")
	    private Integer regionId;

	    @Column(name = "REGION_CD")
	    private String regionCd;

	    @Column(name = "REGION_NAME")
	    private String regionName;

	    @Column(name = "AREA_ID")
	    private Integer areaId;

	    @Column(name = "AREA_CD")
	    private String areaCd;

	    @Column(name = "AREA_NAME")
	    private String areaName;

	    @Column(name = "COMPANY_ID")
	    private Integer companyId;

	    @Column(name = "COMPANY_CD")
	    private String companyCd;

	    @Column(name = "COMPANY_NAME")
	    private String companyName;

	    @Column(name = "ACTIVE_IND")
	    private String activeInd;

	    @Column(name = "USER_ID")
	    private Integer userId;

	    @Column(name = "CREATE_USER")
	    private String createUser;

	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	    @Column(name = "CREATE_DT")
	    private LocalDateTime createDt;

	    @Column(name = "UPDATE_USER")
	    private String updateUser;

	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	    @Column(name = "UPDATE_DT")
	    private LocalDateTime updateDt;

	    @Column(name = "COUNTRY")
	    private String country;

}
