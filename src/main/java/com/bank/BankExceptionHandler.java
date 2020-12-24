package com.bank;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bank.dto.ErrorDetails;
import com.bank.exception.AccountException;
import com.bank.exception.TransactionException;
import com.bank.exception.ValidationException;

/**
 * This is the class to handle all custom exceptions
 * 
 * @author Akhil
 *
 */
@ControllerAdvice
public class BankExceptionHandler extends ResponseEntityExceptionHandler {
	public static String getCurrentTimeStamp() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Date d = new Date();
		String strDate = sdfDate.format(d);
		return strDate;
	}
	// other exception handlers

	/** This method handles exception during account creation
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AccountException.class)
	protected ResponseEntity<Object> handleAccountCreationException(AccountException ex) {
		ErrorDetails error = new ErrorDetails();
		error.setMessage(ex.getMessage());
		error.setTimestamp(getCurrentTimeStamp());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**This method handles the field validation Error
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ValidationException.class)
	protected ResponseEntity<Object> handleValidationException(ValidationException ex) {
		ErrorDetails error = new ErrorDetails();
		error.setMessage(ex.getMessage());
		error.setTimestamp(getCurrentTimeStamp());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**This method handles the transactions exception
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(TransactionException.class)
	protected ResponseEntity<Object> handleTransactionException(TransactionException ex) {
		ErrorDetails error = new ErrorDetails();
		error.setMessage(ex.getMessage());
		error.setTimestamp(getCurrentTimeStamp());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
