package com.target.myretail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Ravi Nandikolla 
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TargetMyRetailException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public TargetMyRetailException(final String message) {
		super(message);
	}

	public TargetMyRetailException(final String message, final Throwable t) {
		super(message, t);
	}
}
