package com.hoppr.bankaccount.exception;

public class AccountIsClosed extends RuntimeException {
  public AccountIsClosed(String message) {
    super(message);
  }
}
