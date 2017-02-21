/**
 * 
 */
package com.sdlc.healthhelp;


public class InvalidInputException extends SpokenException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4563495028652479024L;

	/**
	 * 
	 */

	public InvalidInputException(String message, Throwable cause, String speech) {
		super(message, cause, speech);
	}

	public InvalidInputException(String message, String speech) {
		super(message, speech);
	}



}
