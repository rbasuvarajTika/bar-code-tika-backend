package com.tika.barcode.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryReconcileCollectiveResponse {
	
	private Integer trnInvRecId;
	
	private Integer trnInvRecDetailId;
	
	private Integer accountId;
	
	private Integer itemId;
	
	private String itemCode;
	
	private String batchNo;
	
	private String lotNo;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;
	
	private BigDecimal qtyInHand;
	
	private Integer monthId;
	
	private String reconStatus;
	
	private String reconNotes;
	
	private Integer recCycleId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate reconClosedDate;
	
	private String createdUser;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime createdDate;
	
	private String updatedUser;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime updatedDate;

	public static void save(InventoryReconcileCollectiveResponse record) {
		// TODO Auto-generated method stub
		
	}

	public static Optional<InventoryReconcileCollectiveResponse> findById(Integer trnInvRecId2) {
		// TODO Auto-generated method stub
		return null;
	}
	
		

}
