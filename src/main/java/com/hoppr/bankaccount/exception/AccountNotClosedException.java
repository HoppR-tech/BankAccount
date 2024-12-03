package com.hoppr.bankaccount.exception;

public class AccountNotClosedException extends RuntimeException {
  public AccountNotClosedException(String message) {
    super(message);
  }
}
