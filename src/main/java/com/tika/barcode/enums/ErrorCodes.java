package com.tika.barcode.enums;

/**
 * Enumeration representing {@link ErrorCodes}
 * 
 * @author Raghu
 */

public enum ErrorCodes {

	// Client error responses (400–499)
	BAD_REQUEST(400), UNAUTHORIZED(401), ACCESS_DENIED(403), NOT_FOUND(404), METHOD_NOT_SUPPORTED(405), CONFLICT(409),
	INVALID_OPERATION(412), INVALID_CUSTOMER(413),OK(200),

	// Server error responses (500–599)
	INTERNAL_SERVER_ERROR(500);

	private int code;

	ErrorCodes(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
