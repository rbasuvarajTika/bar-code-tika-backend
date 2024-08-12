package com.tika.barcode.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailsResponse {
	private Integer itemId;
	private String itemNumber;
	private String itemName;
	
}
