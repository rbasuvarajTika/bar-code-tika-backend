package com.tika.barcode.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.tika.barcode.constants.CommonConstants;
import com.tika.barcode.constants.ConsignInventoryConstant;
import com.tika.barcode.constants.ItemDetailsConstant;
import com.tika.barcode.dto.response.ConsignInventoryCountResponse;
import com.tika.barcode.dto.response.ConsignInventoryResponse;

import com.tika.barcode.dto.response.NSServiceResponse;

import com.tika.barcode.service.ConsignInventoryService;
import com.tika.barcode.utility.ResponseHelper;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(ConsignInventoryConstant.CONSIGNMENT_INV_COMMONAPI)
public class ConsignInventoryController {

    @Autowired
    private ConsignInventoryService consignInventoryService;

    @SuppressWarnings("unchecked")
	@GetMapping(ConsignInventoryConstant.CONSIGNMENT_INV_ALL_LIST)
	@CrossOrigin(origins = "*")
	public NSServiceResponse<List<ConsignInventoryResponse>> getAllConsignmentList() {
		 
	    return ResponseHelper.createResponse(new NSServiceResponse<List<ConsignInventoryResponse>>(),
	    		consignInventoryService.getAllConsignmentList(), CommonConstants.SUCCESSFULLY, CommonConstants.ERRROR);
	}
    
    @SuppressWarnings("unchecked")
	@CrossOrigin(origins = "*")
	@GetMapping(ConsignInventoryConstant.CONSIGNMENT_INV_TOTAL_COUNT)
    public ConsignInventoryCountResponse getTotalConsignInventoryCount() {
        Long count = consignInventoryService.getTotalConsignInventoryCount();  
        return new ConsignInventoryCountResponse(count);  
    }
}

