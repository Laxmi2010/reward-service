package com.retailer.rewards.model;

import java.time.LocalDate;

/**
 * Represents a customer's purchase transaction.
 */
public class Transaction {
    private Long customerId;
    private double amount;
    private LocalDate transactionDate;

    public Transaction() {}

    public Transaction(Long customerId, double amount, LocalDate transactionDate) {
        this.customerId = customerId;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
}