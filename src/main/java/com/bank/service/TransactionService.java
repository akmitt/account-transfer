package com.bank.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.bank.dto.TransactionDetails;
import com.bank.entities.Account;
import com.bank.exception.TransactionException;

public interface TransactionService {

	/**
	 * Check if Debit account exists or not and get it if it exists
	 * 
	 * @param account
	 * @return
	 */
	Optional<Account> checkDebitAccount(int account);

	/**
	 * Check if Credit Account exists or not and get details if if exists
	 * 
	 * @param account
	 * @return
	 */
	Optional<Account> checkCreditAccount(int account);

	/**
	 * Updates the Debit Account
	 * 
	 * @param account
	 */
	Account updateDebitAccount(Account account);

	/**
	 * Updates the Credit Account
	 * 
	 * @param account
	 * @return 
	 */
	void updateCreditAccount(Account account);

	
	/**This method do  credit and debit in accounts
	 * @param transactionDetails
	 * @param ifMatch
	 * @return
	 * @throws TransactionException
	 */
	ResponseEntity<?> makeTransaction(TransactionDetails transactionDetails, String ifMatch) throws TransactionException;

}
