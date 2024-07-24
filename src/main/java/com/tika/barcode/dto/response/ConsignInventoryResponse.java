package com.tika.barcode.dto.response;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsignInventoryResponse {
	private Integer accountId;
	private Integer territoryId;
	private Integer itemId;
	private String batchNo;
	private Date expiryDate;
	private BigDecimal totalStock;
	private String materialKey;
	private String customerId;
	private String customerName;
	private Timestamp rfrshDate;

}
