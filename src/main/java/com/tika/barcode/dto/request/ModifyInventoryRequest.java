package com.tika.barcode.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyInventoryRequest {
	private String user;
	private Integer trnInvRecDetailId;
	private String itemCode;
//	private String batchNo;
//	private Date expiryDate;
	private Integer qtyInHand;

}
