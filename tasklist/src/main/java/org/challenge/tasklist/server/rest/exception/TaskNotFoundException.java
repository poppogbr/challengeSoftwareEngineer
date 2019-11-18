package org.challenge.tasklist.server.rest.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class TaskNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final String code = "TASK_NOT_FOUND";
	
	public TaskNotFoundException() {
		super();
	}

	public TaskNotFoundException(String message) {
		super(message);
	}

	public TaskNotFoundException(Throwable cause) {
		super(cause);
	}

	public TaskNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public static String getCode() {
		return code;
	}
}
