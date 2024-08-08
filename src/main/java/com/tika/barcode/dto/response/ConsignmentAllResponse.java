package com.tika.barcode.dto.response;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsignmentAllResponse {
	
	private Integer accountId;

    private String territoryId;

    private Integer itemId;

    private String batch;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;
    private BigDecimal totalStock;

    private String materialKey;

    private String customerId;

    
    private String customerName;
    
    private LocalDateTime rfrsgDate;
   

}
