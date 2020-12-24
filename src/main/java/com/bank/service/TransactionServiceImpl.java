package com.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bank.constants.Constants;
import com.bank.db.AccountRepository;
import com.bank.dto.TransactionDetails;
import com.bank.entities.Account;
import com.bank.exception.TransactionException;

@Component
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	@Transactional
	public synchronized ResponseEntity<?> makeTransaction(TransactionDetails transactionDetails, String ifMatch)
			throws TransactionException {
		// check if debit account number exists
		Optional<Account> debitAccount = checkDebitAccount(transactionDetails.getDebitAccount());
		if (!debitAccount.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		// check if debit account number Etag present in headers and matches or
		// not
		if (ifMatch == null || ifMatch.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		if (!ifMatch.equals(String.valueOf(debitAccount.get().getVersion()))) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
		}
		// check if debit account number has sufficient balance
		if (debitAccount.get().getBalance() < transactionDetails.getTransferAmount()) {
			throw new TransactionException(Constants.INSUFFICIENT_BALANCE);
		}
		// check if credit account number exists
		Optional<Account> creditAccount = checkCreditAccount(transactionDetails.getCreditAccount());

		if (!creditAccount.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		try {
			Account debit = debitAccount.get();
			debit.setBalance(debit.getBalance() - transactionDetails.getTransferAmount());
			Account debit1 = updateDebitAccount(debit);
			Account credit = creditAccount.get();
			credit.setBalance(credit.getBalance() + transactionDetails.getTransferAmount());
			updateCreditAccount(credit);
			return ResponseEntity.ok().eTag("\"" + debit1.getVersion() + "\"").body(debit);
		} catch (OptimisticLockingFailureException exception) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		}

	}

	@Override
	public Optional<Account> checkDebitAccount(int account) {
		Optional<Account> debitAccount = accountRepository.findById(account);
		return debitAccount;
	}

	@Override
	public Optional<Account> checkCreditAccount(int account) {
		Optional<Account> creditAccount = accountRepository.findById(account);
		return creditAccount;
	}

	@Override
	public Account updateDebitAccount(Account account) {
		return accountRepository.save(account);

	}

	@Override
	public void updateCreditAccount(Account account) {
		accountRepository.save(account);

	}

}
