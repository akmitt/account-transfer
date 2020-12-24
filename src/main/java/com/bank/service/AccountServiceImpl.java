package com.bank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bank.constants.Constants;
import com.bank.db.AccountRepository;
import com.bank.dto.AccountDetails;
import com.bank.entities.Account;
import com.bank.exception.AccountException;

@Service
public class AccountServiceImpl {

	@Autowired
	private AccountRepository accountRepository;

	public AccountRepository getAccountRepository() {
		return accountRepository;
	}

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	/**
	 * This method is used to create the account
	 * 
	 * @param accountDetails
	 * @return
	 * @throws BankException
	 */
	public Account createAccount(AccountDetails accountDetails) throws AccountException {
		
		Account account = new Account();
		account.setBalance(accountDetails.getBalance());
		account.setName(accountDetails.getAccountHolderName());
		Account createdAccount = null;
		try {
			createdAccount = accountRepository.save(account);
		} catch (Exception e) {
			throw new AccountException(Constants.ACCOUNt_ERROR);
		}

		return createdAccount;
	}
   public ResponseEntity<?> getAccount(int accountNumber)
   {
	   Optional<Account> account= accountRepository.findById(accountNumber);
	   if (account.isPresent()) {
			return ResponseEntity.ok().eTag("\"" + account.get().getVersion() + "\"").body(account.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	  
   }
	
}
