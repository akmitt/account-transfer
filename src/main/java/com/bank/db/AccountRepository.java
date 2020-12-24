package com.bank.db;

import org.springframework.data.repository.CrudRepository;

import com.bank.entities.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {

}
