package com.tika.barcode.exceptions;

import com.tika.barcode.enums.ErrorCodes;
import lombok.Getter;
import lombok.Setter;

/**
 * Processes an {@link NSException } request.
 * 
 * @author Raghu
 *
 */

@Getter
@Setter
public class NSException extends RuntimeException {

	/**
	 * NS(Next Science)Exception.
	 *
	 */

	private static final long serialVersionUID = 1L;

	private Integer errorCode;

	private String message;

	public NSException(ErrorCodes errorCode, String message) {
		super(message);
		this.errorCode = errorCode.getCode();
		this.message = message;
	}

	public NSException(ErrorCodes errorCode, Exception ex) {
		super(ex);
		this.errorCode = errorCode.getCode();
		this.message = ex.getMessage();
	}

}
