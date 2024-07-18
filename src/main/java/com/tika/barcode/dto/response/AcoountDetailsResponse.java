package com.tika.barcode.dto.response;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcoountDetailsResponse {
	
	private Integer accountId;

    private String accountName;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String zip;


    private Integer itemId;
    private String materialKey;
    private String itemDesc1;
    private String batch;
    private String lotNo;
    private Date expiryDate;
    private BigDecimal qtyInHand;

}
