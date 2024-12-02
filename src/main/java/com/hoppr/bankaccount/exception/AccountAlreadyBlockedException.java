package com.hoppr.bankaccount.exception;

public class AccountAlreadyBlockedException extends RuntimeException {
  public AccountAlreadyBlockedException(String message) {
    super(message);
  }
}
