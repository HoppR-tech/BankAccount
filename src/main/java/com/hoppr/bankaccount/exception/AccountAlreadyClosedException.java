package com.hoppr.bankaccount.exception;

public class AccountAlreadyClosedException extends RuntimeException {
  public AccountAlreadyClosedException(String message) {
    super(message);
  }
}
