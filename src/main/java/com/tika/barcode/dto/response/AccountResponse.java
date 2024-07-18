package com.tika.barcode.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
	
	private Integer accountId;

    private String accountName;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String zip;


    private Integer territoryId;
    private String territoryCd ;
    private String territoryName;





}
