package com.tika.barcode.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tika.barcode.dto.response.ConsignInventoryResponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerrAlignmentDetailsRequest {
	
	//private Integer territoryId;
	private Integer accountId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss:SS")
	private LocalDateTime	rfrshDate;


	private String createUser;
	private Integer oldTerrId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss:SS")
	private LocalDateTime updateDate;
	private String updateUser;


}