package com.tika.barcode.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddInventoryRequest {
	private String user;
	private Integer trnInvRecId;
	private String itemCode;
	private String batchNo;
	private String lotNo;
	private Integer qtyInHand;

}
