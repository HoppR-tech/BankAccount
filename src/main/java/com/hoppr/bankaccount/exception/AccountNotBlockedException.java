package com.hoppr.bankaccount.exception;

public class AccountNotBlockedException extends RuntimeException {
  public AccountNotBlockedException(String message) {
    super(message);
  }
}
