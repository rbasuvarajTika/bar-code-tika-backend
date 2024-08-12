package com.tika.barcode.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    //private String materialKey;
    private String itemNumber;
    //private String itemDesc1;
    private String itemName;
   // private String batch;
    private String lotNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;
    private BigDecimal qtyInHand;
    private String repName;
    private String userName;
   // private String userLogin;

}
