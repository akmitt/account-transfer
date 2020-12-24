package com.bank.dto;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;

/**
 * This is the bean for transaction details
 * 
 * @author Akhil
 *
 */
public class TransactionDetails {
	@ApiModelProperty(notes = "Debit Account Number minimum length is 1")

	@Min(1)
	private Integer debitAccount;
	@ApiModelProperty(notes = "Debit Account Number minimum length is 1")
	@Min(1)
	private Integer creditAccount;
	@ApiModelProperty(notes = "Transfer amount minimum length is 1")
	@Min(1)
	private Integer transferAmount;

	public Integer getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(Integer debitAccount) {
		this.debitAccount = debitAccount;
	}

	public Integer getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(Integer creditAccount) {
		this.creditAccount = creditAccount;
	}

	public Integer getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(Integer transferAmount) {
		this.transferAmount = transferAmount;
	}

}
