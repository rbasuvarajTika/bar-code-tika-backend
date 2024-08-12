package com.tika.barcode.dto.request;

import java.sql.Date;

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
	private String itemName;
	//private String batchNo;
	private String lotNo;
	private Integer qtyInHand;
	private Date expiryDate;

}
