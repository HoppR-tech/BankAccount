package com.hoppr.bankaccount.domain.usecases.reopen;

public class AccountIsNotClosed extends RuntimeException {
  public AccountIsNotClosed(String message) {
    super(message);
  }
}
