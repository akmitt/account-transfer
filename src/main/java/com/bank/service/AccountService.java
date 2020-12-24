package com.bank.service;

import com.bank.dto.AccountDetails;
import com.bank.entities.Account;

public interface AccountService {
	
	/** CReates account number
	 * @param account
	 * @return
	 */
	Account createAccount(AccountDetails account);
	

}
