package com.retailer.rewards.exception;

/**
 * Custom exception for handling invalid transaction scenarios (e.g., negative amounts).
 */
public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String message) {
        super(message);
    }
}