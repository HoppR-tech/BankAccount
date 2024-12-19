package com.hoppr.bankaccount.exception;

public class AccountIsAlreadyClosed extends RuntimeException {
  public AccountIsAlreadyClosed(String message) {
    super(message);
  }
}
