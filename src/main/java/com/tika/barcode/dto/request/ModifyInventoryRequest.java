package com.tika.barcode.dto.request;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyInventoryRequest {
	private String user;
	private Integer trnInvRecDetailId;
	private String itemNumber;
	private String batchNo;
	private String lotNo;
	private Integer qtyInHand;
	private Date expiryDate;

}