package com.bank.exception;

/**
 * This is the exception class which handles the exceptions
 * 
 * @author Akhil
 *
 */
public class AccountException extends Exception {

	private static final long serialVersionUID = -6807464624744472112L;

	public AccountException(String message) {
		super(message);
	}

}
