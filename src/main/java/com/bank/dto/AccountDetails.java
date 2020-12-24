package com.bank.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

/**
 * This is the dto for account details
 * 
 * @author Akhil
 *
 */
public class AccountDetails {

	@NotNull
	@NotEmpty
	@ApiModelProperty(notes="Account Balance to open account should be minimum 5 and max 10")
	@Size(min=5,max = 50, message = "Account Holder name should be minimum of 5 letters and max 50 letters")
	public String accountHolderName;
	@ApiModelProperty(notes="Minimum balance of 100 is needed to open account")

	@Min(100)
	private Integer balance;

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

}
