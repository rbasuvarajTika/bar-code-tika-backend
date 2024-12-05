package com.tika.barcode.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryReconResponse {
	
	private Integer trnInvRecId;
	private Integer accountId;
	private String reconStatus;
	private String reconNotes;
	private String createdUser;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime createdDate;
	private String updatedUser;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime updatedDate;

}
