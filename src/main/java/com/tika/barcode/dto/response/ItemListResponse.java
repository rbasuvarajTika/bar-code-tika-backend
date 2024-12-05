package com.tika.barcode.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemListResponse {
	
	private Integer itemId;
	private String itemCode;
	private String itemNumber;
	private String itemDesc1;
	private String itemDesc2;
	private String itemType;
	private Integer prodLine;
	private Integer itemGroupId;
	private String itemGroupName;
	private Integer productGroupId;
	private String productGroupName;
	private String implantType;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime rfrshDate;

}
