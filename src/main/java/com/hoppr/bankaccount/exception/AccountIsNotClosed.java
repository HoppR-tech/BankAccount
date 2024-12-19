package com.hoppr.bankaccount.exception;

public class AccountIsNotClosed extends RuntimeException {
  public AccountIsNotClosed(String message) {
    super(message);
  }
}
