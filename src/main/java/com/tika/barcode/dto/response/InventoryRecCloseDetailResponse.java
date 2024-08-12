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
public class InventoryRecCloseDetailResponse {
	
	private Integer accountId;
    private String accountName;
    private Integer trnInvRecId;
    private Integer trnInvRecDetailsId;
   // private String batchNo;
    private Integer itemId;
    //private String materialKey;
    private String itemNumber;
    private Integer reconCycleId;
    private String lotNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime reconStartDate;
    private BigDecimal qtyInHand;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate reconClosedDate;
    private String reconStatus;
    
}
