package com.tika.barcode.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitInventoryRequest {
	private String user;
	private Integer trnInvRecId;
	private String reconNotes;

}
