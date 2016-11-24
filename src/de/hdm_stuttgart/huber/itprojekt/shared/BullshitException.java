package de.hdm_stuttgart.huber.itprojekt.shared;

import java.io.Serializable;

public class BullshitException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BullshitException() {
		
	}

	public BullshitException(String message) {
		super(message);
		
	}

	public BullshitException(Throwable cause) {
		super(cause);
		
	}

	public BullshitException(String message, Throwable cause) {
		super(message, cause);
	}

	public BullshitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
