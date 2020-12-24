package com.bank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.db.AccountRepository;
import com.bank.entities.Account;

/** This is test method for account repository
 * @author Akhil
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    AccountRepository account;

    @Test
    public void saveTest() throws Exception {
    	account.save(new Account());
    }
}