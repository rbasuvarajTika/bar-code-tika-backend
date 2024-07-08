package com.tika.barcode.exceptions;

import lombok.Data;

/**
 * Processes an {@link ErrorResponse } request.
 * 
 * @author Raghu
 *
 */

@Data
public class ErrorResponse {

	private int errorCode;

	private String message;

	private Boolean success;
}
