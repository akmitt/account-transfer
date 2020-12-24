package com.bank;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.constants.Constants;
import com.bank.db.AccountRepository;
import com.bank.dto.TransactionDetails;
import com.bank.entities.Account;
import com.bank.exception.AccountException;
import com.bank.exception.TransactionException;
import com.bank.service.TransactionServiceImpl;

/**
 * This is the test class for transaction service
 * 
 * @author Akhil
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {

	@TestConfiguration
	static class TransactionServiceTestContextConfiguration {

		@Bean
		public TransactionServiceImpl transactionService() {
			return new TransactionServiceImpl();
		}
	}

	@Autowired
	private TransactionServiceImpl transactionService;

	@MockBean
	private AccountRepository accountRepo;

	@Before
	public void setUp() {
		Account debitAccount = new Account();
		debitAccount.setName("Akhil");
		debitAccount.setBalance(100);
		debitAccount.setAccountNumber(1000);
		debitAccount.setVersion(0);

		Account creditAccount = new Account();
		creditAccount.setName("Vikas");
		creditAccount.setBalance(200);
		creditAccount.setAccountNumber(1001);
		creditAccount.setVersion(0);

		Mockito.when(accountRepo.findById(1000)).thenReturn(Optional.of(debitAccount));
		Mockito.when(accountRepo.findById(1001)).thenReturn(Optional.of(creditAccount));
		Mockito.when(accountRepo.save(creditAccount)).thenReturn(creditAccount);
		Mockito.when(accountRepo.save(debitAccount)).thenReturn(debitAccount);
	}

	/**
	 * test if credit account is present or not
	 * 
	 */
	@Test
	public void checkCreditAccountTest() {

		Optional<Account> account = transactionService.checkCreditAccount(1001);
		assertTrue(account.isPresent());

	}
	
	/**
	 * @throws AccountException
	 */
	@Test
	public void checkDebitAccountTest() throws AccountException {

		Optional<Account> account = transactionService.checkDebitAccount(1000);
		assertTrue(account.isPresent());

	}
	/**
	 * test method to update debit account
	 * 
	 */
	@Test
	public void updateDebitTest() {
		Account accountDebit = new Account();
		accountDebit.setName("Akhil");
		accountDebit.setBalance(200);
		accountDebit.setAccountNumber(1000);

		transactionService.updateDebitAccount(accountDebit);

	}

	/**
	 * test method to update credit account
	 *
	 */
	@Test
	public void updateCreditTest() {
		Account accountCredit = new Account();
		accountCredit.setName("Akhil");
		accountCredit.setBalance(200);
		accountCredit.setAccountNumber(1001);

		transactionService.updateCreditAccount(accountCredit);

	}

	/**
	 * test method when debit account dont exist
	 * @throws TransactionException 
	 */
	@Test
	public void makeTransactionTestDebitAccountInvalid() throws TransactionException {
		TransactionDetails transactionDetails = new TransactionDetails();
		transactionDetails.setDebitAccount(456);
		ResponseEntity<?> response = transactionService.makeTransaction(transactionDetails, "0");
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}
	@Test
	public void makeTransactionEtagNullOrEmpty() throws TransactionException {
		TransactionDetails transactionDetails = new TransactionDetails();
		transactionDetails.setDebitAccount(1000);
		ResponseEntity<?> response = transactionService.makeTransaction(transactionDetails, null);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		response = transactionService.makeTransaction(transactionDetails, "");
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	@Test
	public void makeTransactionInvalidETag() throws TransactionException {
		TransactionDetails transactionDetails = new TransactionDetails();
		transactionDetails.setDebitAccount(1000);
		ResponseEntity<?> response = transactionService.makeTransaction(transactionDetails, "1");
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
	}

	/**
	 * test method when debit account has less balance
	 */
	@Test
	public void makeTransactionTestDebitAccountInvalidInsufficientBalance() {
		TransactionDetails transactionDetails = new TransactionDetails();
		transactionDetails.setDebitAccount(1000);
		transactionDetails.setTransferAmount(5000);
		try {
			transactionService.makeTransaction(transactionDetails,"0");
			fail();
		} catch (Exception ex) {
			Assertions.assertThat(ex.getMessage()).isEqualTo(Constants.INSUFFICIENT_BALANCE);
		}

	}

	/**
	 * test method when credit account is invalid
	 * @throws TransactionException 
	 */
	@Test
	public void makeTransactionTestCreditInvalid() throws TransactionException {
		TransactionDetails transactionDetails = new TransactionDetails();
		transactionDetails.setDebitAccount(1000);
		transactionDetails.setTransferAmount(50);
		transactionDetails.setCreditAccount(4597);
		
			ResponseEntity<?> response=transactionService.makeTransaction(transactionDetails,"0");
			Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	/**
	 * test method to make valid transaction
	 * 
	 * @throws TransactionException
	 */
	@Test
	public void makeTransactionTest() throws TransactionException {
		TransactionDetails transactionDetails = new TransactionDetails();
		transactionDetails.setDebitAccount(1000);
		transactionDetails.setTransferAmount(50);
		transactionDetails.setCreditAccount(1001);

		ResponseEntity<?> output= transactionService.makeTransaction(transactionDetails,"0");

		assertNotNull(output.getBody());
	}

	
}