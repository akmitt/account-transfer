package com.bank;

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

import com.bank.db.AccountRepository;
import com.bank.dto.AccountDetails;
import com.bank.entities.Account;
import com.bank.exception.AccountException;
import com.bank.service.AccountServiceImpl;

/** This is the test class for account service
 * @author Akhil
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

	@TestConfiguration
	static class AccountServiceTestContextConfiguration {

		@Bean
		public AccountServiceImpl accountService() {
			return new AccountServiceImpl();
		}
	}

	@Autowired
	private AccountServiceImpl accountService;

	@MockBean
	private AccountRepository accountRepo;

	@Before
	public void setUp() {
		Account account = new Account();
		account.setName("Akhil");
		account.setBalance(100);
		account.setAccountNumber(1000);
		account.setVersion(0);
		Mockito.when(accountRepo.findById(1000)).thenReturn(Optional.of(account));
	}

	
	@Test
	public void getAccount() throws AccountException {
		
		ResponseEntity<?> response=accountService.getAccount(1000);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

	}
	@Test
	public void getAccountNotFound() throws AccountException {
		
		ResponseEntity<?> response=accountService.getAccount(1001);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

}