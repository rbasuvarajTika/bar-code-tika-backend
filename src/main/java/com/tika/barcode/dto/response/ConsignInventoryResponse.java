package com.tika.barcode.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsignInventoryResponse {
	private Integer monthId;
	private Integer accountId;
	private Integer territoryId;
	private Integer itemId;
	private String batch;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate expiryDate;
	private BigDecimal totalStock;
	private String materialKey;
	private String customer;
	private String customerName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime rfrshDate;
	private String lotNo;
	private Integer trnInvRecId;
	private String createdUser;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	

}


