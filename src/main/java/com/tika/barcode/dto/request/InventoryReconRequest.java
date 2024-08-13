package com.tika.barcode.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryReconRequest {
	private String user;
	private Integer accountId;
	private String itemNumber;
	private String batchno;
	private String lotNo;
	private Integer qtyInHand;
	private String reconNotes;
	private String itemName;
	private String expiryDate;

}
