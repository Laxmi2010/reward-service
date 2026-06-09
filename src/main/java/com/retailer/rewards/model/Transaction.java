package com.retailer.rewards.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a customer's purchase transaction.
 */
public class Transaction {
	private Long customerId;
	private BigDecimal amount;
	private LocalDate transactionDate;

	public Transaction() {
	}

	public Transaction(Long customerId, BigDecimal amount, LocalDate transactionDate) {
		this.customerId = customerId;
		this.amount = amount;
		this.transactionDate = transactionDate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
}