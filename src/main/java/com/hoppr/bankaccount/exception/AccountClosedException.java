package com.hoppr.bankaccount.exception;

public class AccountClosedException extends RuntimeException {
  public AccountClosedException(String message) {
    super(message);
  }
}
