package org.challenge.tasklist.server.rest.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class FieldRequiredException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final String code = "FIELD_REQUIRED";
	
	public FieldRequiredException() {
		super();
	}

	public FieldRequiredException(String message) {
		super(message);
	}

	public FieldRequiredException(Throwable cause) {
		super(cause);
	}

	public FieldRequiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public static String getCode() {
		return code;
	}
}
